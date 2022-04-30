
<#import 'commontmp.ftlh' as f>


<@f.page>
<#--    <style>-->

<#--    .card-columns {-->
<#--        media-breakpoint-only(lg) {-->
<#--            column-count: 4;-->
<#--        }-->
<#--        <#include media-breakpoint-only(xl)> {-->
<#--            column-count: 5;-->
<#--        }-->
<#--    }-->
<#--    </style>-->

        <form method="get" action="/main" class="form-inline">
            <div class="form-group mx-sm-3 mb-2">
                <input type="text" name="filter" class="form-control" value="${filter?ifExists}" placeholder="search by tag">
            </div>
            <button type="submit" class="btn btn-primary mb-2">search</button>
        </form>
    <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
        Add new message
    </a>
    <div class="collapse" id="collapseExample">
        <div class="form-group mt-3">
            <form method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <input type ="text" class="form-control" name ="text" placeholder="введите сообщение">
                </div>
                <div class="form-group">
                    <input type ="text" class="form-control" name ="tag" placeholder="#хэштег">
                </div>
                <#--            add image-->
                <div class="form-group">
                    <div class="custom-file">
                        <input type="file" name="file" id="customFile">
                        <label class="custom-file-label" for="customFile">choose file</label>
                    </div>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                <div class="form-group">
                    <button type="submit" class="btn btn-primary">POST</button>
                </div>
            </form>
        </div>
    </div>
<!--    <input type="hidden" name="_csrf" value="{{_csrf.token}}" /> use this for each from to sent csrf with request-->

<!--    name of map with all Messages-->
    <div class="card-columns">
        <#list messages as message>
            <div class="card my-3" style="width: 18rem;">
                <#--            add image-->
                <div>
                    <#if message.filename??>
                        <img src="/img/${message.filename}" class="card-img-top">
                    </#if>
                </div>
                <div class="m-2">
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