/**
 * Created by tscheinecker on 28.01.2015.
 */
(function (window, $, undefined) {

    window.teamecho = window.teamecho || {};
    window.teamecho.util = window.teamecho.util || {};

    window.teamecho.util.showConfirmDialog = function (trigger) {
        var $trigger = $(trigger);

        var dialog = $('.modal.confirm');
        dialog.find('.modal-body').text($trigger.data('teamecho-dialog-message'));
        dialog.find('.modal-title').text($trigger.data('teamecho-dialog-title'));

        dialog.find('a.ok')
            .text($trigger.data('teamecho-action-text'))
            .attr('href', $trigger.data('teamecho-action-href'));

        dialog.modal('show');
    };

})(window, jQuery);