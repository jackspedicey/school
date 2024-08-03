const loadDataTable = query => {
    $('#dt-enrollment').DataTable({
        "processing": true,
        "serverSide": true,
        "ajax": {
            "url": "/rest/datatable/upload" + query,
            "type": "POST",
            "dataType": "json",
            "contentType": "application/json",
            "data": function (d) {
                return JSON.stringify(d);
            }
        },
        "columnDefs": [
            {"targets": -1,
                "searchable": false,
                "orderable": false
            }
        ],
        "columns": [
            {"title": "Course Id", "data": "id"},
            {"title": "Course Name", "data": "courseName"},
            {"title": "Course Description", "data": "courseDesc"},
            {"title": "Course Level", "data": "strCourseLevel"},
            {"title": "Lecturer", "data": "teacherName"},
        ],
        "responsive": true,
        "autoWidth": false,
    });
}

const validation = () => {

}

const reloadDatatable = (query) => {
    validation();
    $('#dt-policy').DataTable().destroy();
    loadDataTable(query);
}

const checkQuery = (query) => {
    return query.length > 0 ? '&' : '?';
}

const getQuery = () => {
    let policyNumber = $('#policyNumber').val();
    let agentId = $('#agentId').val();
    let docType = $('#docType').val();
    let resiNo = $('#resiNo').val();
    let expedition = $('#expedition').val();
    let status = $('#status').val();
    let uploadFrom = $('#uploadFrom').val();
    let uploadTo = $('#uploadTo').val();

    let query = ''

    if (policyNumber) {
        query+=checkQuery(query) + "policyNumber="+policyNumber;
    }

    if (agentId) {
        query+=checkQuery(query) + "agentId="+agentId;
    }

    if (docType) {
        query+=checkQuery(query) + "docType="+docType;
    }

    if (resiNo) {
        query+=checkQuery(query) + "resiNo="+resiNo;
    }

    if (expedition) {
        query+=checkQuery(query) + "expedition="+expedition;
    }

    if (uploadFrom) {
        query+=checkQuery(query) + "from="+uploadFrom;
    }

    if (uploadTo) {
        query+=checkQuery(query) + "to="+uploadTo;
    }

    if (status) {
        query+=checkQuery(query) + "status="+status;
    }

    console.log('query: ' + query)

    return query;
}

const filter = () => {
    reloadDatatable(getQuery());
}

const resetField = () => {
    $('#courseName').val("");
    $('#courseDesc').val("");
    $('#courseLevel').val("");
    $('#teacher').val("");
}

let xlsMimeType = 'data:application/vnd.ms-excel';
let xlsxMimeType = 'data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet';

const exportExcel = () => {
    validation();
    let query = getQuery();
    if (query) {
        $.ajax({
            url : 'rest/download/excel' + query,
            type : 'GET',
            headers: {'Content-Type': 'application/vnd.ms-excel'},
            responseType: 'arraybuffer',
            success : function(result) {
                console.log(result)
                let data = new Blob([result], { type: 'data:application/vnd.ms-excel' });
                let url = URL.createObjectURL(data);

                let hiddenElement = document.createElement('a');
                hiddenElement.href = url;
                hiddenElement.target = '_blank';
                hiddenElement.download = "download" + '.xls';
                hiddenElement.click();
            }
        });
    } else {
        alert('please filter before download')
    }
}

$(document).ready(function () {
    loadDataTable('');
});