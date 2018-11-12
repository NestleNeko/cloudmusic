console.log("sssss");
function selectallmusic() {
	$.ajax({
		url : "music/select/selectallmusic",
		data : null,
		dataType : null,
		type : "post",
		success : function(data) {
			var musiclist = data.rows;
			console.log(data.rows);
			var songArr = new Array();
			for (var music of musiclist) {
				console.log(music.mname);
				var duration = Math.floor(music.duration/60);
				var time = duration +":"+ music.duration%60;
				console.log(time);
				var songs = function song(id,name,src,autor,lrcSrc,time)  {
						this.id = "dhgjghi",
                        this.name =  music.mname ,
                        this.src = "",
                        this.autor = music.artist,
                        this.lrcSrc = "",
                        this.time = time
                    };
				songArr.push(songs);
				
			}
			
			
			ajaxInfo.loadDate(songArr);
			
		},
		error : function(data) {
			alert("error");
		}
		
	});
}
