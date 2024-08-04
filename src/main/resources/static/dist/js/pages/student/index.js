let enrollmentTable;

const loadDataTable = () => {
    enrollmentTable = $('#dt-student').DataTable({
        serverSide: true,
        ajax: {
            url: '/api/enrollment/datatable',
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
            { title: 'Course Name', data: 'courseName' },
            { title: 'Description', data: 'courseDesc' },
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
            {
                title: 'Action',
                data: null,
                render: function(data, type, row) {
                    return '<button class="btn btn-info btn-sm detail-btn" data-id="' + row.enrollmentId + '">Detail</button>';
                }
            }
        ],
        responsive: true,
        autoWidth: false
    });
};

const stringifyLevel = (level) => {
    switch (level) {
        case "1":
        case 1:
            return 'Beginner';
        case "2":
        case 2:
            return 'Intermediate';
        case "3":
        case 3:
            return 'Expert';
        default:
            return 'Unknown';
    }
};

const showCourseDetails = (enrollmentId) => {
    $.ajax({
        url: '/api/enrollment/' + enrollmentId,
        type: 'GET',
        success: function(data) {
            let detailsHtml = `
                <h5>${data.courseName}</h5>
                <p><strong>Description:</strong> ${data.courseDesc}</p>
                <p><strong>Level:</strong> ${stringifyLevel(data.level)}</p>
                <p><strong>Schedule:</strong> ${data.schedule}</p>
                <p><strong>Lecturer:</strong> ${data.teacherName}</p>
            `;
            console.log(JSON.stringify(data))
            $('#course-details').html(detailsHtml);
            $('#unroll-button').data('id', enrollmentId);
            $('#modal-course-detail').modal('show');
        },
        error: function(xhr, status, error) {
            console.error('Error fetching course details:', error);
        }
    });
};

const unrollCourse = (enrollmentId) => {
    $.ajax({
        url: '/api/enrollment/' + enrollmentId,
        type: 'DELETE',
        contentType: 'application/json',
        success: function(response) {
            alert('Successfully leave course!');
            $('#modal-course-detail').modal('hide');
            enrollmentTable.ajax.reload();
        },
        error: function(xhr, status, error) {
            alert('Error leave course: ' + error);
        }
    });
};

const enableEdit = () => {
    $('#studentForm input').prop('disabled', false);
    $('#saveButton').show();
    $('#editButton').hide();
}

const saveEdit = () => {
    const studentData = {
        id: $('#studentId').val(),
        firstName: $('#firstName').val(),
        lastName: $('#lastName').val(),
        email: $('#email').val(),
        address: $('#address').val(),
        birthDate: $('#birthdate').val()
    };

    $.ajax({
        url: '/student/update',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(studentData),
        success: function(response) {
            alert('Successfully edited student');
            window.location.href = '/student';

        },
        error: function(xhr, status, error) {
            alert('Error editing student: ' + error);
        }
    });
};

const disableEdit = () => {
    $('#studentForm input').prop('disabled', true);
    $('#saveButton').hide();
    $('#editButton').show();
}

$(document).ready(function () {
    loadDataTable();
    disableEdit();

    $(document).on('click', '.detail-btn', function() {
        let enrollmentId = $(this).data('id');
        showCourseDetails(enrollmentId);
    });

    $('#unroll-button').click(function() {
        let enrollmentId = $(this).data('id');
        unrollCourse(enrollmentId);
    });
});