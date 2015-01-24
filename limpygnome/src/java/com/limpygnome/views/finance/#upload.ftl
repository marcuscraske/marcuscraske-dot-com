<#global title="Finance - Upload">

<h2>
    <a href="/finance">Finance</a> - Upload
</h2>
<form method="post" action="/finance/upload" enctype="multipart/form-data">
    <div class="table">
        <div class="row">
            <div class="cell2">
                <div class="p">
                    File #1:
                </div>
            </div>
            <div class="cell2">
                <div class="p">
                    <input type="file" name="fileupload1" />
                </div>
            </div>
            <div class="clear"></div>
        </div>
        <div class="row">
            <div class="cell2">
                <div class="p">
                    File #2:
                </div>
            </div>
            <div class="cell2">
                <div class="p">
                    <input type="file" name="fileupload2" />
                </div>
            </div>
            <div class="clear"></div>
        </div>
        <div class="row">
            <div class="cell2">
                <div class="p">
                    File #3:
                </div>
            </div>
            <div class="cell2">
                <div class="p">
                    <input type="file" name="fileupload3" />
                </div>
            </div>
            <div class="clear"></div>
        </div>
        <div class="row">
            <div class="cell2">
                <div class="p">
                    File #4:
                </div>
            </div>
            <div class="cell2">
                <div class="p">
                    <input type="file" name="fileupload4" />
                </div>
            </div>
            <div class="clear"></div>
        </div>
        <div class="row">
            <div class="cell2">
                <div class="p">
                    File #5:
                </div>
            </div>
            <div class="cell2">
                <div class="p">
                    <input type="file" name="fileupload5" />
                </div>
            </div>
            <div class="clear"></div>
        </div>
        <div class="row">
            <div class="cell2">
                <div class="p">
                    File #6:
                </div>
            </div>
            <div class="cell2">
                <div class="p">
                    <input type="file" name="fileupload6" />
                </div>
            </div>
            <div class="clear"></div>
        </div>
        <div class="row">
            <div class="cell2">
                <div class="p">
                    File #7:
                </div>
            </div>
            <div class="cell2">
                <div class="p">
                    <input type="file" name="fileupload7" />
                </div>
            </div>
            <div class="clear"></div>
        </div>
        <div class="row">
            <div class="cell2">
                <div class="p">
                    File #8:
                </div>
            </div>
            <div class="cell2">
                <div class="p">
                    <input type="file" name="fileupload8" />
                </div>
            </div>
            <div class="clear"></div>
        </div>
        <div class="row">
            <div class="cell1 tac">
                <div class="p">
                    <input type="submit" value="Upload" />
                </div>
            </div>
            <div class="clear"></div>
        </div>
        <div class="clear"></div>
    </div>
</form>

<#if finance_success??>
    <p class="success">
        ${finance_success?html}
    </p>
</#if>

<#if finance_error??>
    <p class="error">
        ${finance_error?html}
    </p>
</#if>

<#if finance_info??>
    <p class="info">
        ${finance_info?html?replace("\n", "<br />")}
    </p>
</#if>
