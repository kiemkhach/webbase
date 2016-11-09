var detailLabDialog;
var EDIT_MODE = 1;
var CREATE_MODE = 2;
$(document).ready(function(){
	detailLabDialog = $('#detailLabDialog').data('dialog');
	$('#tblLab').dataTable({
	    'searching' : true
	});
	$('#btnSave').click(function(){
		var mode = parseInt($(this).attr('mode'));
		if(EDIT_MODE == mode){
			updateLab();
		}else{
			saveLab();
		}
	});
	$('#btnCreate').click(function(){
		clearForm();
		$('#btnSave').attr('mode',CREATE_MODE);
		detailLabDialog.open();
	});
});
function editLab(element){
	getLabByClient(element.id);
	$('#btnSave').attr('mode',EDIT_MODE);
	detailLabDialog.open();
}
function deleteLab(element){
	var result = confirm('Are you sure you want to delete this item?');
    if (result) {
        $.ajax({
            url: 'deleteLab.action',
            data: {
            	labId: element.id
            },
            traditional: true,
            async: false,
            success: function (data) {
                var rs = JSON.parse(data);
                if (rs.result == 'success') {
                    $.Notify({
                        caption: 'Success',
                        content: 'Remove lab success',
                        type: 'success'
                    });
                    location.reload();
                } else {
                    $.Notify({
                        caption: 'Failed',
                        content: 'Remove lab failed',
                        type: 'alert'
                    });
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(XMLHttpRequest.responseText);
            }
        });
    }
}
function getLabByClient(labId){
	if(labId != null && typeof labId != 'undefined'){
		$.ajax({
			url: 'getLabById.action?labId='+labId,
			traditional: true,
			async: false,
			success: function(data){
				var obj = JSON.parse(data);
				if(obj != null){
					bindDataToForm(obj);
				}
	        },
	        error: function (XMLHttpRequest, textStatus, errorThrown) {
	            console.log(XMLHttpRequest.responseText);
	        }
		});
	}
}
function updateLab(){
	var obj = getFormData();
	var data = {
	   lab : obj
	};
	$.ajax({
		url: 'updateLab.action',
		type: 'POST',
		data: JSON.stringify(data),
		dataType: 'json',
		contentType: 'application/json;charset=utf-8',
		async: false,
	    beforeSend: function () {
	        $('#btnSave').addClass('loading-cube');
	        $('#btnSave').addClass('lighten');
	        $('#btnSave').text('Saving...');
	    },
	    success: function (data) {
            console.log(data);
            var rs = JSON.parse(data);
            if (rs.result == 'success') {
                $('#btnSave').removeClass('primary loading-cube ');
                $('#btnSave').addClass('success');
                $('#btnSave').attr('disabled', 'disabled');
                $('#btnSave').text('Success');
                location.reload(true);
            } else {
                console.log(rs);
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log(XMLHttpRequest.responseText);
        }
	});
}
function saveLab(){
	var obj = getFormData();
	var data = {
	   lab : obj
	};
	$.ajax({
		url: 'saveLab.action',
		type: 'POST',
		data: JSON.stringify(data),
		dataType: 'json',
		contentType: 'application/json;charset=utf-8',
		async: false,
	    beforeSend: function () {
	        $('#btnSave').addClass('loading-cube');
	        $('#btnSave').addClass('lighten');
	        $('#btnSave').text('Saving...');
	    },
	    success: function (data) {
            console.log(data);
            var rs = JSON.parse(data);
            if (rs.result == 'success') {
                $('#btnSave').removeClass('primary loading-cube ');
                $('#btnSave').addClass('success');
                $('#btnSave').attr('disabled', 'disabled');
                $('#btnSave').text('Success');
                location.reload(true);
            } else {
                console.log(rs);
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log(XMLHttpRequest.responseText);
        }
	});
}
function getFormData(){
	var id = ($('#txtClientId').val() != '') ? parseInt($('#txtClientId').val()) : null;
	var name = $('#txtLabName').val();
	var url = $('#txtUrl').val();
	var uri = $('#txtUri').val();
	var logoPath = $('#txtLogoPath').val();
	var columnKey = $('#txtColumnKey').val();
	var columnSite = $('#txtColumnSite').val();
	var tableSite =  $('#txtTableSite').val();
	var lab = new Lab(id, url, uri, name, logoPath, columnSite, tableSite, columnKey);
	return lab;
}


function bindDataToForm(obj){
	$('#txtClientId').val(obj.id);
	$('#txtLabName').val(obj.name);
	$('#txtUrl').val(obj.url);
	$('#txtUri').val(obj.uri);
	$('#txtLogoPath').val(obj.logoPath);
	$('#txtColumnKey').val(obj.columnKey);
	$('#txtColumnSite').val(obj.columnSite);
	$('#txtTableSite').val(obj.tableSite);
}
function clearForm(){
	$('#txtClientId').val('');
	$('#txtLabName').val('');
	$('#txtUrl').val('');
	$('#txtUri').val('');
	$('#txtLogoPath').val('');
	$('#txtColumnKey').val('');
	$('#txtColumnSite').val('');
	$('#txtTableSite').val('');
}

function Lab(id,url,uri,name,logoPath,columnSite,tableSite,columnKey){
	this.id = id;
	this.url = url;
	this.uri = uri;
	this.name = name;
	this.logoPath = logoPath;
	this.columnSite = columnSite;
	this.tableSite = tableSite;
	this.columnKey = columnKey;
}

