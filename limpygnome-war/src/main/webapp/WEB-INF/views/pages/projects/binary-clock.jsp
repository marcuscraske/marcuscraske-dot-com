<h1>
    Overview
</h1>
<p>
    The purpose of this project was to experiment with the Raspberry Pi, electronics and C++; I also wanted to
    create a new binary-clock with internet-updated time, the ability to set multiple alarms by using a
    web-browser (alarm clock), a multi-threaded custom-written web-server and room automation based on sensor data.
</p>

<h1>
    Gallery
</h1>

<h1>
    Using a Basic Shift Register
</h1>
<h2>
    Hardware Communication
</h2>
<p>
    Due to the number of limited outputs on the Raspberry Pi, it's practical to use a <i>shift-register</i> to virtually increase the
    number of outputs; for a basic 24-hour binary clock, you will require at least 17 LEDs - which would consume nearly all of the usable
    output ports on the Raspberry Pi.
</p>
<p>
    The following section is about the 74HC595, an integrated-circuit with 16 pins; the 74HC595 is practical because eight pins can be
    utilized as output's and multiple IC's can be connected in series - it's also very cheap. To control 17 LEDs to display binary time, we
    shall use three shift registers (allowing for up to 24 outputs)...which requires <b>only three output pins</b> from our Raspberry Pi.
</p>
<p>
    Pin layout:
</p>
<p class="large-image">
    <a href="/content/projects/binary-clock/shift_registers_pin_layout.png">
        <img src="/content/projects/binary-clock/shift_registers_pin_layout.png" class="thumb" style="width: 14em; height: 14em;" alt="Shift Registers Pin Layout" />
    </a>
</p>

<p>
    To control each of the eight output pins (O1-O8), we must communicate using three pins (which are connected to our Raspberry Pi):
</p>

<ul>
    <li>
        <b>Latch</b> - we set the latch to low when we want to write new data to our shift register; we then put it back to high when we're finished.
    </li>
    <li>
        <b>Data</b> - specifies if an output pin is enabled.
    </li>
    <li>
        <b>Clock</b> - we toggle the clock, by setting it to high and then low, to get the shift register to read the value of the data-pin.
    </li>
</ul>

<p>
    Therefore to set the eight outputs of a shift register to on/off, we would do the following:
</p>

<ul>
    <li>
        Set the latch to low.
    </li>
    <li>
        For each output pin in order from 1 to 8:
    </li>
    <li>
        Set the data-pin to high or low to specify if a pin is either enabled or disabled.
    </li>
    <li>
        Toggle the clock by setting it to high and then setting it back to low; this will cause the shift register to set the pin
        either on/off based on the data-pin.
    </li>
    <li>
        Set the latch back to high.
    </li>
</ul>

<p>
    When we want to control more than eight pins, i.e. multiple shift-registers in-series, we simply repeat step 2 (setting which pins to
    be on/off) sixteen times. However each shift-register in-series is connected like a chain; every shift-register after the first has its
    data-pin connected to the previous shift-register's overflow pin (refer to pin-layout). Therefore when the first eight pins have been
    set on the primary shift-register, the data signal is pushed to the overflow pin - which goes to the data-pin of the next/second
    shift-register; this continues until the latch is set back to high at the end.
</p>

<p>
    The clock and latch is therefore connected to each shift-register using the same circuit, hence for our 17 LEDs we end-up with the following
    circuit (click to open the image):
</p>
<p class="large-image">
    <a href="/content/projects/binary-clock/shift_registers_circuit_diagram.png">
        <img class="thumb" src="/content/projects/binary-clock/shift_registers_circuit_diagram.png" alt="Shift Registers Circuit Diagram" style="width=; height=;" />
    </a>
</p>

<p>
    This diagram may look scary at first, however follow the circuit for the data-pin and re-read the above paragrah - you should see that
    the primary/first shift-register is the right-most shift-register.
</p>

<h2>
    Software Communication
</h2>
<p>
    In this video, a simple binary count is performed to test the cirucit on a bread-board:
</p>

<div class="tac">
    <iframe width="420" height="315" src="//www.youtube.com/embed/bl5ir-yKsBQ" frameborder="0" allowfullscreen></iframe>
</div>

<p>
    I do not have the actual code available for the above test, due to corruption with the Debian image; however the code used the
    <i>shiftOut</i> function of the WiringPi library, which worked something like this:
</p>

<div class="code">
    <pre class="brush: cpp">
        uint8_t pinData = 0, pinLatch = 2, pinClock = 3;

        pinMode(pinData, OUTPUT);
        pinMode(pinLatch, OUTPUT);
        pinMode(pinClock, OUTPUT);

        while(true)
        {
          for(int i = 0; i < 8; i++)
          {
            digitalWrite(pinLatch, LOW);
            shiftOut(pinData, pinClock, MSBFIRST, i);
            digitalWrite(pinLatch, HIGH);
            usleep(1000 * 100); // 100 m/s delay
          }
        }
    </pre>
</div>

<p>
    I then improved the code by creating a wrapper to allow for multiple shift-registers:
</p>

<div class="code">
    <pre class="brush: cpp">
        uint8_t pinData = 0, pinLatch = 2, pinClock = 3;

        pinMode(pinData, OUTPUT);
        pinMode(pinLatch, OUTPUT);
        pinMode(pinClock, OUTPUT);

        while(true)
            for(int i = 1; i <= 32768; i*= 2)
            {
                IC_74HC595::write(2, pinData, pinLatch, pinClock, i);
                //std::cout << i << std::endl;
                usleep(1000 * 10); // 10 m/s delay
            }
    </pre>
</div>

<p>
    The write member-function:
</p>

<div class="code">
    <pre class="brush: cpp">
        inline static void write(uint8_t numberOfShiftRegisters, uint8_t pinData, uint8_t pinClock, uint8_t pinLatch, int value)
        {
            // Place latch to low, to write data
            digitalWrite(pinLatch, LOW);
            // Write the eight bits
            for(int i = (8 * numberOfShiftRegisters) - 1; i >= 0; --i)
            {
                // Set data-pin to on or off
                digitalWrite(pinData, value & (1 << i));
                //std::cout << "74HC: " << i << " - " << (value & (1 << i) ? 1 : 0) << "!" << std::endl;
                // Inform the IC to read the data-pin
                digitalWrite(pinClock, HIGH);
                digitalWrite(pinClock, LOW);
            }
            // Put the latch back to high
            digitalWrite(pinLatch, HIGH);
        }
    </pre>
</div>

<h1>
    Analogue Sensors
</h1>
<p>
    Since my new binary clock will be responsible for partial room-automation, I wanted to add sensors; using sensor data and specified
    thresholds, certain electrical appliances can be turned on/off etc.
</p>

<h2>
    Hardware Communication
</h2>
<p>
    Since I decided to use a photo-resistor and TMP36 temperature sensor, we require analogue inputs to read their values; however the
    Raspberry Pi does not have any analogue inputs (only digital / high and low). Therefore the solution is to use an analogue-to-digital
    converter, which translates an analogue signal (0 to 1023 - since the MCP3008 has a 10-bit resolution / 2^10-1=1023 ~ we subtract
    one since we start at zero of-course) to on/off (compatible with our Raspberry Pi); I have decided to use the MCP3008 because it's
    fairly simple to use.
</p>

<p>
    Pin layout:
</p>
<p class="large-image">
    <a href="/content/projects/binary-clock/adc_pin_layout.png">
        <img src="/content/projects/binary-clock/adc_pin_layout.png" alt="ADC Pin Layout" class="thumb" style="width: 14em; height: 14em;" />
    </a>
</p>

<p>
    The MCP3008 requires four pins on the Raspberry Pi:
</p>

<ul>
    <li>
        <b>Clock</b> - used to specify when the data-in pin has been set; the same principal as the clock from the shift-registers
        previously mentioned; this will also be used to read bits out as well.
    </li>
    <li>
        <b>Data-out</b> - used for reading bits, or rather high's and/or low's, which represent the numeric value of a
        pin (from 0 to 1023 due to being analogue).
    </li>
    <li>
        <b>Data-in</b> - used to specify which pin's value to output on the data-out pin.
    </li>
    <li>
        <b>Chip-select</b> - a similar function to the latch of a shift-register in this context, click
        <a href="http://en.wikipedia.org/wiki/Chip_select">here</a> for more information.
    </li>
</ul>

<h2>
    Software Communication
</h2>
<p>
    According to the <a href="http://ww1.microchip.com/downloads/en/DeviceDoc/21295d.pdf">data-sheet</a>, the following
    takes place to read a single-pin on the MCP3008 (which has eight analogue inputs):
</p>
<p class="large-image">
    <a href="/content/projects/binary-clock/adc_communication.png">
        <img src="/content/projects/binary-clock/adc_communication.png" alt="ADC Communication" class="thumb" />
    </a>
</p>

<h3>
    Transmitting the Pin to be Read
</h3>
<p>
    Therefore to read a pin we need to transmit the following on the <i>data-in</i> pin:
</p>

<ul>
    <li>
        Specify high (1) as our start-bit.
    </li>
    <li>
        Specify high (1) to state the input-mode as <i>single-ended</i> 
    </li>
    <li>
        Three bits representing a number between 0 to 7, stating which pin to read; if you do not know binary:
    </li>
    <ul>
        <li>
            000 = 0
        </li>
        <li>
            001 = 1
        </li>
        <li>
            010 = 2
        </li>
        <li>
            011 = 3
        </li>
        <li>
            100 = 4
        </li>
        <li>
            101 = 5
        </li>
        <li>
            110 = 6
        </li>
        <li>
            111 = 7
        </li>
    </ul>
</ul>

<h3>
    Reading the Value of the Pin
</h3>
<p>
    After the above takes place, we then need to read data from the <i>data-out</i> pin:
</p>

<ol>
    <li>
        Read an empty-bit.
    </li>
    <li>
        Read a null-bit.
    </li>
    <li>
        Read ten bits representing the value (from 0-1023 / 2^10 - 10 bits) of the analogue pin.
    </li>
</ol>

<p>
    During each read, we will toggle the clock (set it to high and then low) to inform the MCP3008 we have
    read the <i>data-out</i> pin.
</p>

<h3>
    Putting It Together
</h3>
<p>
    Hence to read the value of a pin on the MCP3008, using the previously mentioned procedures and communication
    diagram, we do the following:
</p>

<ol>
    <li>
        Inform the MCP3008 we want to read a pin, using a procedure equivalent to the latch with shift-registers:
    </li>
    <ol>
        <li>
            Set the chip-select pin to high.
        </li>
        <li>
            Set the clock pin to low.
        </li>
        <li>
            Set the chip-select pin to low.
        </li>
    </ol>
    <li>
        Write five-bits of data to inform the MCP3008 of the pin to be transmitted:
    </li>
    <ol>
        <li>
            Two bits set to high (our start-bit and to state the transmit mode as <i>single-input</i>).
        </li>
        <li>
            Three bits representing a value from 0-7, corresponding to the pin to be read.
        </li>
    </ol>
    <li>
        Read 12-bits of data:
    </li>
    <ol>
        <li>An empty-bit.</li>
        <li>A null-bit.</li>
        <li>Ten bits representing the value of the pin from 0 to 1023.</li>
    </ol>
    <li>
        Inform the MCP3008 we have finished this cycle; again, equivalent to a latch with shift-registers:
    </li>
    <ol>
        <li>
            Set the chip-select to high.
        </li>
    </ol>
</ol>
<p>
    This is the following function I wrote, with help from a Pastebin entry by Dvanhaeke for transmitting the
    five-bits:
</p>

<div class="code">
    <pre class="brush: cpp">
        int IC_MCP3008::read(MCP3008_PINS pin, uint8_t pinDataIn, uint8_t pinDataOut, uint8_t pinClock, uint8_t pinChipSelect)
        {
            if(pin < 0 || pin > 7)
                return -1;
            // With help from: http://pastebin.com/62rXCzej
            // Toggle chip-select for the MCP3008 to accept clock low-state
            digitalWrite(pinChipSelect, HIGH);
            digitalWrite(pinClock, LOW);
            digitalWrite(pinChipSelect, LOW);

            // Write 5-bits of data to the MCP3008
            // Structure:
            //          < 11 to state start-bit and single-ended input mode >
            //          < three bits 0-7 to state the pin being read >
            // Source: http://ww1.microchip.com/downloads/en/DeviceDoc/21295d.pdf, page 19
            int value = pin;        // Pin (0-7) being read
            value |= 0x18;          // pin + 11000 (24 / 0x18)
            value <<= 3;            // Shift three places 11< pin >000
            for(int i = 0; i < 5; i++)
            {
                // 10000000 (0x80 / 128)
                digitalWrite(pinDataIn, value & 0x80 ? HIGH : LOW);
                value <<= 1;
                // Toggle clock to cause the MCP3008 to read the value of the data-in pin
                digitalWrite(pinClock, HIGH);
                digitalWrite(pinClock, LOW);
            }

            // Read 12-bits form the MCP3008 (null bit, empty bit and 10 bits representing the value of the pin being read)
            value = 0;
            for(int i = 0; i < 12; i++)
            {
                // Toggle clock to cause the MCP3008 to write the next bit of data to data-out pin
                digitalWrite(pinClock, HIGH);
                digitalWrite(pinClock, LOW);
                // Shift the current value by one, ready for the next bit of data to be read
                value <<= 1;
                // Read the next bit of data
                if(digitalRead(pinDataOut) == HIGH)
                    value |= 0x1;
            }
            // Toggle chip-select pin, end of read
            digitalWrite(pinChipSelect, HIGH);
            // Get rid of the first bit (null)
            value /= 2;
        }
    </pre>
</div>

<p>
    If you're still unsure, I would also recommend reading the article by
    <a href="http://jeremyblythe.blogspot.co.uk/2012/09/raspberry-pi-hardware-spi-analog-inputs.html">Jeremy Blythe</a> from
    his article on the MCP3008.
</p>

<h2>
    Hardware Sensors
</h2>
<p>
    I am using the MCP3008 to read the temperature and the amount of light using a photo-resistor; the circuit looks
    like the following:
</p>

<p class="large-image">
    <a href="/content/projects/binary-clock/adc_circuit_diagram.png">
        <img src="/content/projects/binary-clock/adc_circuit_diagram.png" alt="ADC Circuit Diagram" style="width: 20em; height: 20em;" class="thumb" />
    </a>
</p>

<h3>
    Photo-Resistor - Light Sensor
</h3>
<p>
    The photo-resistor simply requires 3.3v to one pin, it doesn't matter which end; then you need to wire the other pin to an analogue-input
    and have the same wire also connected to a 10k resistor, which is then connected to ground. The way the photo-resistor works is by
    specifying a value between 0 to 1023 (for a 10-bit analogue-to-digital converter) based on dark to light; hence in a pitch-black
    room, the photo-resistor should read 0 - therefore in a very bright room, it should read 1023. We can then convert this to a
    percentage using very basic maths:
</p>

\begin{align}
(input value / 1023) * 100
\end{align}

<h3>
    TMP36 - Temperature Sensor
</h3>
<p>
    The wiring of pins on the TMP36 [b]does matter[/b]; with the flat-side of the TMP36 facing you, the pins should be
    wired in the following order:
</p>

<ul>
    <li>
        3.3v input
    </li>
    <li>
        Analogue input
    </li>
    <li>
        Ground (no resistor required)
    </li>
</ul>

<p>
    The TMP36 will also output a value between 0 to 1023 (for a 10-bit analogue-to-digital converter); we convert it to
    celcius using the following formula:
</p>

\begin{align}
(voltage~in~millivolts - 500) / 10 = celcius
\end{align}

<p>
    To convert our 3.3v analogue signal to millivolts, we use the following formula:
</p>

\begin{align}
analogue * (3.3 * 1000) / 1024 = analogue~in~millivolts
\end{align}

<p>
    Hence we end up with the following formula to convert the raw analogue signal to a temperature in celcius:
</p>

\begin{align}
((analogue * ((3.3 * 1000) / 1024)) - 500) / 10 = celcius
\end{align}

<p>
    The code I wrote to read the TMP36:
</p>

<div class="code">
    <pre class="brush: cpp">
        inline static double getTemperature_Celcius(double volts, IC_MCP3008::MCP3008_PINS pin, uint8_t pinDataIn, uint8_t pinDataOut, uint8_t pinClock, uint8_t pinChipSelect)
        {
                return ((getRaw(pin, pinDataIn, pinDataOut, pinClock, pinChipSelect) * ((volts * 1000) / 1024)) - 500) / 10; // ([voltage in millivolts] - 500) / 10; volts to millivolts = [value] * ([[volts e.g. 3.3 or 5]v * 1000] / 1024)
        }
    </pre>
</div>

<h1>
    Tips
</h1>
<h2>
    Debugging with Root
</h2>
<p>
    I did try the following with the WirePi library:
</p>

<div class="code">
    <pre class="brush: plain">
        sudo chown root binaryclockv2
        sudo chmod 4755 binaryclockv2
    </pre>
</div>

<p>
    Even though no errors were thrown and the setup was successful, I could not set a GPIO to high; additionally this
    had to be set after every build. Instead I simply did:
</p>

<div class="code">
    <pre class="brush: plain">
        sudo passwd root
    </pre>
</div>

<p>
    ...and then I connected, via remote development, using the root account. The above command sets the password for
    the root account, which by default is not set on Debian squeeze (the distribution used on my Raspberry Pi).
</p>

<p>
    This is bad security practice, however the Raspberry Pi was on a secure network and the context was strictly for
    debugging.  You can alternatively, apparently, install the WirePi library as programs, which can be executed
    without sudo/super-user privileges, however I decided against this due to decreased speed.
</p>

<h2>
    Constructing &amp; Debugging Physical Circuit Boards
</h2>
<p>
    I would recommend purchasing a digital multimeter and testing your circuits; for this binary clock, I used
    sockets (which are used to hold integrated circuits such as the 74HC595 shift-register). I would then leave
    the sockets empty and use my multimeter to test the voltage through each pin to check no pins were touching.
    Using a socket will also prevent heat-damage to the IC from a soldering iron.
</p>

<h1>
    Useful Links
</h1>
<p>
    Below are a collection of links I found to be useful during this project; some of these links may contain
    articles written more towards your learning-style or contain more technical information.
</p>

<ul>
    <li>
        WiringPi library, a wrapper for the GPIO pins:<br />
        <a href="https://projects.drogon.net/raspberry-pi/wiringpi/">https://projects.drogon.net/raspberry-pi/wiringpi/</a>
    </li>
    <li>
        Useful information regarding the GPIO pins:<br />
        <a href="http://elinux.org/RPi_Low-level_peripherals">http://elinux.org/RPi_Low-level_peripherals</a>
    </li>
    <li>
        Interfacing with the MCP3008 (analogue to digital converter) using a Raspberry Pi:<br />
        <a href="http://jeremyblythe.blogspot.co.uk/2012/09/raspberry-pi-hardware-spi-analog-inputs.html">http://jeremyblythe.blogspot.co.uk/2012/09/raspberry-pi-hardware-spi-analog-inputs.html</a>
    </li>
    <li>
        A freeware application used for the circuit-drawings in this article:<br />
        <a href="http://fritzing.org/">http://fritzing.org/</a>
    </li>
</ul>

<h2>
    Source-Code
</h2>
<p>
    The source-code for this project can be found at Github:
</p>
<p>
    <a href="https://github.com/limpygnome/binary_clock_v2">https://github.com/limpygnome/binary_clock_v2</a>
</p>
