
<#import 'commontmp.ftlh' as f>
<#import 'logintmp.ftlh' as l>

<@f.page>
<div>
    <@l.logout />
</div>
    <div>
        <form method="post">
            <input type ="text" name ="text" placeholder="введите сообщение">
            <input type ="text" name ="tag" placeholder="#хэштег">
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <button type="submit">Добавить</button>
        </form>
    </div>
    <div>Список сообщений</div>
<!--    <input type="hidden" name="_csrf" value="{{_csrf.token}}" /> use this for each from to sent csrf with request-->
    <form method="get" action="/main">
        <input type="text" name="filter" value="${filter}">
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
        </div>
    <#else>
    No message
</#list>
</@f.page>