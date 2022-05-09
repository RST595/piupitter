<#include "security.ftlh">
<!--    name of map with all Messages-->
<div class="row row-cols-2" id="message-list">
    <#list messages as message>
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
            <div class="card-footer text-muted">
                <a href="/user-messages/${message.author.id}">${message.authorName}</a>
                <#if message.author.id == currentUserId>
                    <a class="btn btn-primary" href="/user-messages/${message.author.id}?message=${message.id}">
                        edit
                    </a>
                </#if>
            </div>
        </div>
    <#else>
        No message
    </#list>
</div>