
<#import 'commontmp.ftlh' as f>
<#import 'logintmp.ftlh' as l>

<@f.page>
Login page
<@l.login "/login" />
<!--link to page-->
<a href="/registration">Add new user</a>
</@f.page>