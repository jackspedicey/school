let enrollmentTable;

const loadDataTable = () => {
    let teacherId = $('#teacherId').val();
    enrollmentTable = $('#dt-teacher').DataTable({
        serverSide: true,
        ajax: {
            url: '/api/course/datatable/' + teacherId,
            type: 'POST',
            contentType: 'application/json',
            data: function(d) {
                return JSON.stringify({
                    draw: d.draw,
                    start: d.start,
                    length: d.length,
                    searchValue: d.search.value,
                    sortColumn: d.columns[d.order[0].column].data,
                    sortDirection: d.order[0].dir
                });
            }
        },
        columns: [
            { title: 'Course Name', data: 'name' },
            { title: 'Description', data: 'description' },
            {
                title: 'Level',
                data: 'courseLevel',
                render: function(data, type, row) {
                    switch (data) {
                        case "1": return 'Beginner';
                        case "2": return 'Intermediate';
                        case "3": return 'Expert';
                        default: return '';
                    }
                }
            },
            { title: 'Schedule', data: 'schedule' },
            { title: 'Lecturer', data: 'teacherName' },
        ],
        responsive: true,
        autoWidth: false
    });
};

const enableEdit = () => {
    $('#teacherForm input').prop('disabled', false);
    $('#saveButton').show();
    $('#editButton').hide();
}

const saveEdit = () => {
    const teacherData = {
        id: $('#teacherId').val(),
        firstName: $('#firstName').val(),
        lastName: $('#lastName').val(),
        email: $('#email').val(),
        address: $('#address').val(),
        birthDate: $('#birthdate').val()
    };

    $.ajax({
        url: '/teacher/update',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(teacherData),
        success: function(response) {
            alert('Successfully edited teacher');
            window.location.href = '/teacher';

        },
        error: function(xhr, status, error) {
            console.error(xhr.responseText);
            alert('Error updating teacher: ' + error);
        }
    });
};

const disableEdit = () => {
    $('#teacherForm input').prop('disabled', true);
    $('#saveButton').hide();
    $('#editButton').show();
}

$(document).ready(function () {
    loadDataTable();
    disableEdit();
});