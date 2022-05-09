<a class="btn btn-primary mx-sm-3 mb-2" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
    Message editor
</a>
<div class="collapse <#if message??>show</#if>" id="collapseExample">
    <div class="form-group mt-3">
        <form method="post" enctype="multipart/form-data">
            <div class="form-group mx-sm-3 mb-2">
                <input type ="text" class="form-control ${(textError??)?string('is-invalid', "")}"
                       value="<#if message??>${message.text}</#if>" name ="text" placeholder="message" />

                <#--                    show error message if we have text+"Error" from @Valid-->
                <#if textError??>
                    <div class="invalid-feedback">
                        ${textError}
                    </div>
                </#if>
            </div>
            <div class="form-group mx-sm-3 mb-2">
                <input type ="text" class="form-control"
                       value="<#if message??>${message.tag}</#if>" name ="tag" placeholder="#hashtag" />
                <#--                    show error message if we have text+"Error" from @Valid-->
                <#if tagError??>
                    <div class="invalid-feedback">
                        ${tagError}
                    </div>
                </#if>
            </div>
            <#--            add image-->

            <div class="form-group mx-sm-3 mb-2">
                <div class="custom-file">
                    <input type="file" name="file" id="customFile" />
                    <label class="custom-file-label" for="customFile"></label>
                </div>
            </div>
            <!--    use line bellow for each form to sent csrf with request-->
            <input type="hidden" name="_csrf" value="${_csrf.token}" />

            <input type="hidden" name="id" value="<#if message??>${message.id}</#if>" />
            <div class="form-group">
                <button type="submit" class="btn btn-primary mx-sm-3 mb-2">POST</button>
            </div>
        </form>
    </div>
</div>