<#global title="PALS - Programming Assessment and Learning System">
<#include "includes/header.ftl">

<h3>Overview</h3>
<p>
    PALS is an <a href="https://github.com/limpygnome/pals">open source</a> assessment system for programming assignments,
    which uses dynamic and static analysis techniques for automated marking. Its architecture allows for multiple nodes to
    process work simultaneously, with dynamic analysis executing student code within a sandboxed environment. Plugins can also
    be hotswapped and automatically reloaded across all nodes, allowing for extended functionality to be added and changed without
    restarting the system.
</p>

<p>
    I recommend reading the <a href="#report">report</a> for a detailed technical explanation of the system.
</p>

<h3>Features</h3>
<ul>
    <li>Multiple question types:</li>
    <ul>
        <li>Java code - allows editing, compilation and automated testing of code written in Java. Submission can be through an editor or by uploading a zip file.</li>
        <li>Written responses - for essays/text answers, can be manually and automatically marked.</li>
        <li>Multiple choice and response - randomly ordered options, persisted for an instance of an assignment.</li>
    </ul>
    <li>Multiple criterias combined to form the overall mark for a question, which can be given different weightings.</li>
    <li>Multiple criteria types for marking questions:</li>
    <ul>
        <li>Regex matching.</li>
        <li>Code metrics - lines of code, blank lines, comment lines, average identifier length (classes, methods or fields).</li>
        <li>
            Testing inputs - execute code with a specified or random set of inputs. This is executed against both student and correct code, with the
            outputs compared to determine correctness.
        </li>
        <li>Standard input/output testing.</li>
        <li>Existence of classes, methods, fields or enums (including values).</li>
        <li>Java inheritance and interfaces.</li>
        <li>Custom code capable of providing a mark between 0 to 100 for a criteria. Custom feedback can be also provided to the student.</li>
    </ul>
    <li>Security:</li>
    <ul>
        <li>XSS (cross-site scripting) and CSRF (cross-site request forgery) protection.</li>
        <li>Custom session management using SHA-256 hashes for session identifiers, using guidelines from OWASP.</li>
        <li>Captcha protection to prevent automated brute-forcing.</li>
        <li>Passwords hashed with SHA-512 with unique salts for each password.</li>
        <li>Secure inter-node communication using RMI with SSL.</li>
    </ul>
    <li>Aggregation of exceptions, warnings and issues found with code. Useful for addressing common mistakes by students.</li>
    <li>Hotswappable plugins architecture, allowing for a plugin to be added/changed from a central file share and (re)loaded in every node.</li>
    <li>Mass enrollment.</li>
</ul>

<h3>Download</h3>
<p>
    Releases can be found at:
</p>
<p>
    <a href="https://github.com/limpygnome/pals/releases">https://github.com/limpygnome/pals/releases</a>
</p>

<h3 id="report">Project Report</h3>
<p>
    Click <a href="/content/projects/pals/report.pdf">here</a> to download the final project report.
</p>

<h3>Screenshots</h3>
<div class="table">
    <div class="row">
        <div class="cell2">
            <div class="p">
                <a href="/content/projects/pals/criteria_results_inputs.png" class="screenshot">
                    <img src="/content/projects/pals/criteria_results_inputs.png" alt="Criteria results" />
                    <span>
                        The results from dynamic assessment of student code and correct code.
                    </span>
                </a>
            </div>
            <div class="clear"></div>
        </div>
        <div class="cell2">
            <div class="p">
                <a href="/content/projects/pals/criteria_results_output.png" class="screenshot">
                    <img src="/content/projects/pals/criteria_results_output.png" alt="Standard in out testing" />
                    <span>
                        Dynamic testing of standard input/output.
                    </span>
                </a>
            </div>
            <div class="clear"></div>
        </div>
        <div class="clear"></div>
    </div>
    <div class="row">
        <div class="cell2">
            <div class="p">
                <a href="/content/projects/pals/stats_hint.png" class="screenshot">
                    <img src="/content/projects/pals/stats_hint.png" alt="Feedback hint" />
                    <span>
                        Feedback providing the student with a hint of the issue wrong.
                    </span>
                </a>
            </div>
            <div class="clear"></div>
        </div>
        <div class="cell2">
            <div class="p">
                <a href="/content/projects/pals/stats.png" class="screenshot">
                    <img src="/content/projects/pals/stats.png" alt="Stats" />
                    <span>
                        Stats for common issues.
                    </span>
                </a>
            </div>
            <div class="clear"></div>
        </div>
        <div class="clear"></div>
    </div>
    <div class="row">
        <div class="cell2">
            <div class="p">
                <a href="/content/projects/pals/testing_graph.png" class="screenshot">
                    <img src="/content/projects/pals/testing_graph.png" alt="Distributed test" />
                    <span>
                        Graph of time to mark assessments with 1 to 5 nodes simultaneously.
                    </span>
                </a>
            </div>
            <div class="clear"></div>
        </div>
        <div class="cell2">
            <div class="p">
                <a href="/content/projects/pals/erd.png" class="screenshot">
                    <img src="/content/projects/pals/erd.png" alt="ERD" />
                    <span>
                        ERD for PALS.
                    </span>
                </a>
            </div>
            <div class="clear"></div>
        </div>
        <div class="clear"></div>
    </div>
    <div class="row">
        <div class="cell2">
            <div class="p">
                <a href="/content/projects/pals/home.png" class="screenshot">
                    <img src="/content/projects/pals/home.png" alt="Home" />
                    <span>
                        The home page.
                    </span>
                </a>
            </div>
            <div class="clear"></div>
        </div>
        <div class="cell2">
            <div class="p">
                <a href="/content/projects/pals/nodes.png" class="screenshot">
                    <img src="/content/projects/pals/nodes.png" alt="Nodes" />
                    <span>
                        Administration page for nodes.
                    </span>
                </a>
            </div>
            <div class="clear"></div>
        </div>
        <div class="clear"></div>
    </div>
    <div class="row">
        <div class="cell2">
            <div class="p">
                <a href="/content/projects/pals/plugins.png" class="screenshot">
                    <img src="/content/projects/pals/plugins.png" alt="Plugins" />
                    <span>
                        Administration page for plugins.
                    </span>
                </a>
            </div>
            <div class="clear"></div>
        </div>
        <div class="cell2">
            <div class="p">
                <a href="/content/projects/pals/enrollment.png" class="screenshot">
                    <img src="/content/projects/pals/enrollment.png" alt="Enrollment" />
                    <span>
                        Mass enrollment page.
                    </span>
                </a>
            </div>
            <div class="clear"></div>
        </div>
        <div class="clear"></div>
    </div>
    <div class="clear"></div>
</div>
