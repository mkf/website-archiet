function monthtimes() {
	xhr = new XMLHttpRequest();
	var url="https://wakatime.com/@ArchieT/5448ed1b-50cb-44d8-b8cd-e340ff35160d.json";
	xhr.open("GET",url,true);
	xhr.setRequestHeader("Content-Type","application/json");
	xhr.onreadystatechange = function() {
		var give = JSON.parse(xhr.responseText);
		var data = [ ];
		for (var i=0;i<give.data.length;i++) {
			data.push([give.data[i].range.text,give.data[i].grand_total.total_seconds]);
		}
//		var 
	};
	xhr.send();
}

function langues(url,id,title) {
	xhr = new XMLHttpRequest();
	xhr.open("GET",url,true);
	xhr.setRequestHeader("Content-Type","application/json");
	xhr.onreadystatechange = function() {
		var give = JSON.parse(xhr.responseText);
		var data = [ ];
		for (var i=0;i<give.data.length;i++) {
			data.push([give.data[i].name,give.data[i].percent]);
		}
		var options = {
			title: title,
		};
		var chart=new google.visualization.PieChart(document.getElementById(id));
		chart.draw(data,options);
	};
	xhr.send();
}
