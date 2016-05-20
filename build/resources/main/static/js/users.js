showHideCompany($('#roles').val());
$('#roles').on('change', function () {
    var roleValues = $('#roles').val()
    showHideCompany(roleValues);
});

function showHideCompany(role) {
    if (role == null) {
        $('#company').hide();
        return;
    }
    var index;
    var found = false;
    var selectedRoles = role;
    for (index = 0; index < selectedRoles.length; index++) {
        var currentSelRole = selectedRoles[index];
        if (currentSelRole == 'ROLE_COMPANY') {
            found = true;
        }

    }
    if (found) {
        $('#company').show();
    } else {
        $('#company').hide();
    }

}

showHideNewCompany($('#companies').val());
$('#companies').on('change', function () {
    showHideNewCompany(this.value);
})
function showHideNewCompany(user) {
    if (user == '0') {
        $('#newcompany').show();
    }
    else {
        $('#newcompany').hide();
    }
}

showHidePassword($('#changePassword').attr('checked'));
$('#changePassword').on('change', function () {
    showHidePassword(this.checked);
})
function showHidePassword(checked) {
    if (checked) {
        $('#passwordChecked').show();

    } else {
        $('#passwordChecked').hide();
    }
}
showHideLockedReason($('#locked').attr('checked'));
$('#locked').on('change', function () {
    showHideLockedReason(this.checked);
})
function showHideLockedReason(checked) {
    if (checked) {
        $('#lockReason').show();

    } else {
        $('#lockReason').hide();
    }
}
showHideCheckBox($('#id').val())
function showHideCheckBox(id) {
    if (id > 0) {
        $('#passwordCheckBox').show();
    } else {
        $('#passwordCheckBox').hide();
        $('#passwordChecked').show();
    }
}


