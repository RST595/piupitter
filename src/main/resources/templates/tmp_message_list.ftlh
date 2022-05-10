<#include "security.ftlh">
<#import "tmp_pager.ftlh" as p>
<!--    name of map with all Messages-->
<@p.pager url page />
    <div class="row row-cols-2" id="message-list">
        <#list page.content as message>
            <div class="card mx-sm-3 mb-5" style="width: 18rem;" data-id="${message.id}">
                <#--            add image-->
                <div>
                    <#if message.filename??>
                        <img src="/img/${message.filename}" class="card-img-top" />
                    </#if>
                </div>
                <div class="my-2">
                    <span>${message.text}</span><br/>
                    <i>#${message.tag}</i>
                </div>
                <!--            take this field from method message.getAuthorName();-->
                <div class="card-footer text-muted container">
                    <div class="col mx-2">
                        <a class="col align-self-center mx-2" href="/user-messages/${message.author.id}">${message.authorName}</a>
                        <a class="col align-self-center mx-2" href="/messages/${message.id}/like">
                        <#if message.meLiked>
                            <i class="fas fa-heart mx-2"></i>
                        <#else>
                            <i class="far fa-heart mx-2"></i>
                        </#if>
                        ${message.likes}
                        </a>
                        <#if message.author.id == currentUserId>
                            <a class="col btn btn-primary mx-2" href="/user-messages/${message.author.id}?message=${message.id}">
                                edit
                            </a>
                        </#if>
                    </div>
                </div>
            </div>
        <#else>
            No message
        </#list>
    </div>
<@p.pager url page />
