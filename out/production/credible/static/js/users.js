showHideCompany($('#roles :selected').text());

$('#roles').on('change', function () {
    showHideCompany(this.value);
});

function showHideCompany(role) {
    if (role == 'ROLE_COMPANY') {
        $('#company').show();
    }
    else {
        $('#company').hide();
    }
}
