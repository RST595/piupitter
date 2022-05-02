
<#import 'commontmp.ftlh' as f>
<#import 'logintmp.ftlh' as l>

<@f.page>
    ${message?ifExists}
<@l.login "/login" false/>
</@f.page>