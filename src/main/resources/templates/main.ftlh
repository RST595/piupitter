<#import 'commontmp.ftlh' as f>

<@f.page>
    <div class="input-group mb-2">
        <input type="text" name="filter" class="form-control mx-sm-3 mb-2" value="${filter?ifExists}" placeholder="search by tag">
        <div class="input-group-append">
            <button type="submit" class="btn btn-primary px-3">search</button>
        </div>
    </div>
    <a class="btn btn-primary mx-sm-3 mb-2" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
        Add new message
    </a>
    <div class="collapse <#if message??>show</#if>" id="collapseExample">
        <div class="form-group mt-3">
            <form method="post" enctype="multipart/form-data">
                <div class="form-group mx-sm-3 mb-2">
                    <input type ="text" class="form-control ${(textError??)?string('is-invalid', "")}"
                           value="<#if message??>${message.text}</#if>" name ="text" placeholder="message">

<#--                    show error message if we have text+"Error" from @Valid-->
                    <#if textError??>
                        <div class="invalid-feedback">
                            ${textError}
                        </div>
                    </#if>
                </div>
                <div class="form-group mx-sm-3 mb-2">
                    <input type ="text" class="form-control"
                           value="<#if message??>${message.tag}</#if>" name ="tag" placeholder="#hashtag">
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
                        <input type="file" name="file" id="customFile">
                        <label class="custom-file-label" for="customFile"></label>
                    </div>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                <div class="form-group">
                    <button type="submit" class="btn btn-primary mx-sm-3 mb-2">POST</button>
                </div>
            </form>
        </div>
    </div>
    <!--    <input type="hidden" name="_csrf" value="{{_csrf.token}}" /> use this for each from to sent csrf with request-->

    <!--    name of map with all Messages-->
    <div class="row row-cols-2">
        <#list messages as message>
            <div class="card mx-sm-3 mb-5" style="width: 18rem;">
                <#--            add image-->
                <div>
                    <#if message.filename??>
                        <img src="/img/${message.filename}" class="card-img-top">
                    </#if>
                </div>
                <div class="my-2">
                    <span>${message.text}</span>
                    <i>${message.tag}</i>
                </div>
                <!--            take this field from method message.getAuthorName();-->
                <div class="card-footer text-muted">
                    ${message.authorName}
                </div>
            </div>
        <#else>
            No message
        </#list>
    </div>
</@f.page>