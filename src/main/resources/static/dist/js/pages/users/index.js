const add = () => {
    $.ajax({
        url: '/users/add',
        type: 'get',
        dataType: 'html',
        data: {
            edit: "y"
        },
        success: function (result) {
            $('#modal-detail-api').find('.modal-body').html(result);
            $('#modal-detail-api').modal('show');
        }
    });
}

const view = (value) => {
    $.ajax({
        url: '/users/detail',
        type: 'get',
        dataType: 'html',
        data: {
            id: value
        },
        success: function (result) {
            $('#modal-view').find('.modal-body').html(result);
            $('#modal-view').modal('show');
        }
    });
}

const edit = (value) => {
    $.ajax({
        url: '/users/detail',
        type: 'get',
        dataType: 'html',
        data: {
            id: value,
            edit: "y"
        },
        success: function (result) {
            $('#modal-detail-api').find('.modal-body').html(result);
            $('#modal-detail-api').modal('show');
        }
    });
}

const del = value => {
    $.ajax({
        url: '/users/req/delete',
        type: 'get',
        dataType: 'html',
        data: {
            id: value
        },
        success: function (result) {
            $('#modal-detail-api').find('.modal-body').html(result);
            $('#modal-detail-api').modal('show');
        }
    });
}

$(document).ready(function () {
    $('#dt-users').DataTable({
        "buttons": [{
            text: 'My button',
            action: function ( e, dt, node, config ) {
                alert( 'Button activated' );
            }
        }],
        "columns": columns,
        "columnDefs": [{defaultContent: "~", targets: "_all"}],
        "data": rows,
        "autoWidth": false,
        "responsive": true,
        "ordering": false,
    });
});