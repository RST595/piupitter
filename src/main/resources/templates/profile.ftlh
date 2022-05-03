<#import 'commontmp.ftlh' as f>

<@f.page>
    <h5>${username}</h5>
    ${message?ifExists}
    <form method="post">
        <div class="form-group">
            <label class="col-sm-2 col-form-label" > Password:</label>
            <div class="col-sm-6">
                <input type="password" name="password" class = "form-control" placeholder="password"/>
            </div>
        </div>
            <div class="form-group">
                <label class="col-sm-2 col-form-label" > Email:</label>
                <div class="col-sm-6">
                    <input type="email" name="email" class = "form-control" placeholder="e-mail" value="${email!''}"/>
                </div>
            </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button class="btn btn-primary" type="submit" >save</button>
    </form>
</@f.page>