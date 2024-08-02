const loadDataTable = query => {
    $('#dt-policy').DataTable({
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
            {"title": "No Polis", "data": "policyNumber"},
            {"title": "Agent Code", "data": "agentId"},
            {"title": "Doc Type", "data": "docType"},
            {"title": "Send Date", "data": "strSendDate"},
            {"title": "No Resi", "data": "resiNo"},
            {"title": "Kurir", "data": "expedition"},
            {"title": "Status", "data": "status"},
            {"title": "Notes", "data": "notes"},
            {"title": "Uploader", "data": "createdBy"},
            {"title": "Upload Date", "data": "strCreatedTime"}
        ],
        "responsive": true,
        "autoWidth": false,
    });
}

const validation = () => {
    let uploadFrom = $('#uploadFrom').val();
    let uploadTo = $('#uploadTo').val();

    if ((uploadFrom && !uploadTo) || (!uploadFrom && uploadTo)) {
        throw alert('please fill both date')
    }
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
    $('#policyNumber').val("");
    $('#agentId').val("");
    $('#docType').val("");
    $('#resiNo').val("");
    $('#expedition').val("");
    $('#uploadFrom').val("");
    $('#uploadTo').val("");
    $('#status').val("");
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
    $('#uploadFile').on("change",function() {
        const name = $('#uploadFile')[0].files[0].name;
        $(this).prev('label').text(name);
    });
});