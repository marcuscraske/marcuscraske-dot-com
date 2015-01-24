<#global title="Auth - Login">

<h2>
    <a href="/auth">Auth</a> - Welcome!
</h2>

<h3>
    Login
</h3>
<div class="table">
    <div class="row">
        <div class="cell2">
            <div class="p">
                Username:
            </div>
            <div class="clear"></div>
        </div>
        <div class="cell2">
            <div class="p">
                <input type="text" name="username" value="<#if username??>${username?html}</#if>" />
            </div>
            <div class="clear"></div>
        </div>
        <div class="clear"></div>
    </div>
    <div class="row">
        <div class="cell2">
            <div class="p">
                Password:
            </div>
            <div class="clear"></div>
        </div>
        <div class="cell2">
            <div class="p">
                <input type="password" name="password" value="<#if password??>${password?html}</#if>" />
            </div>
            <div class="clear"></div>
        </div>
        <div class="clear"></div>
    </div>
    <div class="row">
        <div class="cell1 tac">
            <div class="p">
                <input type="submit" value="Login" /> <input type="reset" value="Reset" />
            </div>
            <div class="clear"></div>
        </div>
        <div class="clear"></div>
    </div>
    <div class="clear"></div>
</div>

<h3>
    Recovery
</h3>
<p>
    Not available.
</p>
