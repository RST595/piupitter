<#import 'tmp_common.ftlh' as c>

<@c.page>
    <div class="input-group mb-2">
        <input type="text" name="filter" class="form-control mx-sm-3 mb-2" value="${filter?ifExists}" placeholder="search by tag">
        <div class="input-group-append">
            <button type="submit" class="btn btn-primary px-3">search</button>
        </div>
    </div>
    <#include 'tmp_message_edit.ftlh' />
    <#include 'tmp_message_list.ftlh' />

</@c.page>