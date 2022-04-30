<#import 'commontmp.ftlh' as f>
<#import 'logintmp.ftlh' as l>

<@f.page>
    <div class="mb-1">Add new user</div>

<!--if model from controller contain message, then show it-->
${message?ifExists}

<@l.login "/registration" true/>
</@f.page>