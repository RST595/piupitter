<#import 'tmp_common.ftlh' as c>
<#import 'tmp_login.ftlh' as l>

<@c.page>
    <div class="mb-1">Add new user</div>

<!--if model from controller contain message, then show it-->
${message?ifExists}

<@l.login "/registration" true/>
</@c.page>