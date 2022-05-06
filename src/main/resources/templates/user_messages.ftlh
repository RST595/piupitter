<#import 'tmp_common.ftlh' as c>

<@c.page>
    <#if isCurrentUser>
        <#include 'tmp_message_edit.ftlh' />
    </#if>
    <#include 'tmp_message_list.ftlh' />
</@c.page>