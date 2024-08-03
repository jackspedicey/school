const loadDataTable = () => {
    $('#dt-course').DataTable({
        serverSide: true,
        ajax: {
            url: '/api/course/datatable',
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
            { title: 'name', data: 'courseName' },
            { title: 'description', data: 'courseDesc' },
            {
                title: 'level',
                data: 'courseLevel',
                render: function(data, type, row) {
                    // Map numeric levels to descriptive labels
                    switch (data) {
                        case "1":
                            return 'Beginner';
                        case "2":
                            return 'Intermediate';
                        case "3":
                            return 'Expert';
                        default:
                            return ''; // Handle other cases if needed
                    }
                }
            },
            { title: 'schedule', data: 'schedule' },
            { title: 'Lecturer', data: 'teacherName' }
        ],
        responsive: true,
        autoWidth: false
    });
};

const validation = () => {

}

function createCourse() {
    const courseData = {
        courseName: document.getElementById('courseName').value,
        courseLevel: document.getElementById('courseLevel').value,
        courseDesc: document.getElementById('courseDesc').value,
        schedule: document.getElementById('schedule').value
    };

    console.log("create course :" + JSON.stringify(courseData));

    $.ajax({
        url: '/api/course',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(courseData),
        success: function(result) {
            console.log('Success:', result);
            // Refresh the course list or add the new course to the table
        },
        error: function(xhr, status, error) {
            console.error('Error:', error);
            console.log('Status:', status);
            console.log('Response:', xhr.responseText);
        }
    });
}

$(document).ready(function () {
    loadDataTable();
});