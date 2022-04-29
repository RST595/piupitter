<#import 'commontmp.ftlh' as f>
<#import 'logintmp.ftlh' as l>

<@f.page>
Add new user

<!--if model from controller contain message, then show it-->
${message?ifExists}

<@l.login "/registration" />
</@f.page>