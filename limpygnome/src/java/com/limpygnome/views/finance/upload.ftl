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
        </div>
        <div class="row">
            <div class="cell2">
                <div class="p">
                    File #2:
                </div>
            </div>
            <div class="cell2">
                <div class="p">
                    <input type="file" name="fileupload1" />
                </div>
            </div>
        </div>
        <div class="row">
            <div class="cell2">
                <div class="p">
                    File #3:
                </div>
            </div>
            <div class="cell2">
                <div class="p">
                    <input type="file" name="fileupload1" />
                </div>
            </div>
        </div>
        <div class="row">
            <div class="cell2">
                <div class="p">
                    File #4:
                </div>
            </div>
            <div class="cell2">
                <div class="p">
                    <input type="file" name="fileupload1" />
                </div>
            </div>
        </div>
        <div class="row">
            <div class="cell2">
                <div class="p">
                    File #5:
                </div>
            </div>
            <div class="cell2">
                <div class="p">
                    <input type="file" name="fileupload1" />
                </div>
            </div>
        </div>
        <div class="row">
            <div class="cell2">
                <div class="p">
                    File #6:
                </div>
            </div>
            <div class="cell2">
                <div class="p">
                    <input type="file" name="fileupload1" />
                </div>
            </div>
        </div>
        <div class="row">
            <div class="cell1 tac">
                <div class="p">
                    <input type="submit" value="Upload" />
                </div>
            </div>
        </div>
    </div>
</form>

<#if finance_error??>
    <div class="error">
        ${finance_error?html}
    </div>
</#if>

<#if finance_output??>
    <div class="info">
        ${finance_output?html}
    </div>
</#if>
