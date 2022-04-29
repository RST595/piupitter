
<#import 'commontmp.ftlh' as f>
<#import 'logintmp.ftlh' as l>

<@f.page>
<div>
    <@l.logout />
    <span><a href="/user">User list</a></span>
</div>
    <div>
        <form method="post" enctype="multipart/form-data">
            <input type ="text" name ="text" placeholder="введите сообщение">
            <input type ="text" name ="tag" placeholder="#хэштег">
            <input type="file" name="file">
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <button type="submit">Добавить</button>
        </form>
    </div>
    <div>Список сообщений</div>
<!--    <input type="hidden" name="_csrf" value="{{_csrf.token}}" /> use this for each from to sent csrf with request-->
    <form method="get" action="/main">
        <input type="text" name="filter" value="${filter?ifExists}}">
        <button type="submit">Найти</button>
    </form>
<!--    name of map with all Messages-->
<#list messages as message>
        <div>
            <b>${message.id}</b>
            <span>${message.text}</span>
            <i>${message.tag}</i>
<!--            take this field from method message.getAuthorName();-->
            <strong>${message.authorName}</strong>
            <div>
               <#if message.filename??>
                   <img src="/img/${message.filename}">
               </#if>
            </div>
        </div>
    <#else>
    No message
</#list>
</@f.page>