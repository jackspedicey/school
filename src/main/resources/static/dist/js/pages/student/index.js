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
                    return '<button class="btn btn-info btn-sm detail-btn" data-id="' + row.id + '">Detail</button>';
                }
            }
        ],
        responsive: true,
        autoWidth: false
    });
};

const showCourseDetails = (courseId) => {
    $.ajax({
        url: '/api/course/' + courseId,
        type: 'GET',
        success: function(data) {
            let detailsHtml = `
                <h5>${data.name}</h5>
                <p><strong>Description:</strong> ${data.description}</p>
                <p><strong>Level:</strong> ${data.level}</p>
                <p><strong>Schedule:</strong> ${data.schedule}</p>
                <p><strong>Lecturer:</strong> ${data.teacherName}</p>
            `;
            $('#course-details').html(detailsHtml);
            $('#enroll-button').data('id', courseId);
            $('#modal-course-detail').modal('show');
        },
        error: function(xhr, status, error) {
            console.error('Error fetching course details:', error);
        }
    });
};

const enrollCourse = (courseId) => {
    $.ajax({
        url: '/api/enrollment/' + courseId,
        type: 'POST',
        contentType: 'application/json',
        success: function(response) {
            alert('Successfully enrolled in the course!');
            $('#modal-course-detail').modal('hide');
            enrollmentTable.ajax.reload();
        },
        error: function(xhr, status, error) {
            alert('Error enrolling in the course: ' + error);
        }
    });
};

$(document).ready(function () {
    loadDataTable();

    $(document).on('click', '.detail-btn', function() {
        let courseId = $(this).data('id');
        showCourseDetails(courseId);
    });

    $('#enroll-button').click(function() {
        let courseId = $(this).data('id');
        enrollCourse(courseId);
    });
});