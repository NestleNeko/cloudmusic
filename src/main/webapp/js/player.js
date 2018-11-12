/*
    成员变量：
        id: 播放器存放于某个容器中
        src: 播放的地址
        title: 歌名
        author: 演唱者
*/ 
/*
    组件封装的步骤：  --音乐播放组件
        1.创建一个音乐播放器
        2.播放音乐
        3.暂停音乐
        4.显示歌曲时间
        5.音量的控制
        6.进度的展示
        7.上一首下一首
        8.添加音乐
        9.静音
        10.歌词部分
        11.播放模式切换
*/ 
//播放音乐对象   (闭包回调函数)
var playerMusic = {
    audioDom: null,
    arrSongs: [],
    index: 0,
    playModel: "shunxunLoop",
    //初始化话页面
    playEndBack:function(){
        //返回index
    },
    init: function() {
        //创建一个音乐对象
        this.audioDom = document.createElement("audio");
        this.audioDom.volume = 0.5;
        this.endEvent();
    },
    //添加音乐
    addMusic: function(arrsrc){
        for(var i =0;i<arrsrc.length;i++){
            //将音乐放入音乐容器中
            this.arrSongs.push(arrsrc[i]);
        };
        console.log(this.arrSongs)
        //初始化播放第一首
        if(!this.audioDom.src)this.audioDom.src=this.arrSongs[0];
        return this.arrSongs
    },
    //列表歌曲时间
    listTime: function(arrlis,callback){
        var $this = this;
        var lisLength = arrlis.length;
        //递归写法 自己调用自己
        function getTimearr(arr,timearr,index=0,callback){
            $this.audioDom.src = arrlis[index];
            $this.audioDom.oncanplaythrough = function() {
                var totalTime = this.duration;
                var timer = $this.formateTime(totalTime);
                var json = {
                    time: timer,
                    duration: totalTime,
                };
                timearr.push(json);
                if(index == 8)debugger;
                if(index == lisLength-1) {
                    callback&&callback.call(timearr,timearr)
                }else{
                    getTimearr(arr,timearr,++index,callback)
                }
            }
        }
        getTimearr(arrlis,[],0,callback);
    },
    //播放音乐
    play: function() {
        //播放音乐
        this.audioDom.play();
        // console.log(this.index)
    },
    //播放音乐
    player: function(val) {
        if(val===0||val){
            this.index = val;
        }
        //从音乐的数组中取对应的一首进行播放
        this.audioDom.src = this.arrSongs[this.index];
        //音乐播放
        this.audioDom.play();
    },
    //播放的
    playIndex: function(callback){
        var json = {
            num: this.index
        };
        callback.call(json);
    },
    //暂停音乐
    stop: function() {
        //暂停
        this.audioDom.pause();
        flag = 0;
    },
    //上一首下一首 以及模式切换
    endEvent(){
        var $this =this;
        this.audioDom.onended =function(){
            $this.nextIndex("nature");
        }
    },
    nextIndex(src){
        var $this =this;
            var mark = true;
            if($this.playModel == "listLoop"){
                if(src == "next"||src == "nature"){
                    $this.index = ++$this.index;
                    if($this.index == $this.arrSongs.length){
                        $this.index = 0;
                    }
                }else if(src == "pre"){
                    if($this.index == 0) {
                        $this.index = $this.arrSongs.length-1;
                    }else{
                        $this.index--;
                    }
                }
            }else if($this.playModel == "singleLoop"){
                if(src == "next"){
                        $this.index++;
                        mark = true;
                }else if(src == "pre"){
                    $this.index--;
                    
                }else if(src == "nature"){
                    $this.index = $this.index;
                }
                $this.index = $this.index;
            }else if($this.playModel == "randomLoop"){
                $this.index = Math.floor(Math.random()*$this.arrSongs.length);
            }else if($this.playModel == "shunxunLoop"){
                if(src == "next"||src == "nature"){
                    $this.index = ++$this.index;
                    if($this.index == $this.arrSongs.length){
                        $this.index = $this.arrSongs.length-1
                    }
                }else if(src == "pre"){
                    if($this.index == 0) {
                        $this.index = 0;
                    }else{
                        $this.index--;
                    }
                }
            }
            this.player();
            $this.playEndBack($this.index);
    },
    //上一首
    // prev: function() {
    //     console.log(this.index +"===="+ this.arrSongs.length)
    //     if(this.index == 0) {
    //         console.log(this.index )
    //         this.index = 0;
    //     }else{
    //         this.index--;
    //     }
    //     this.player();
    // },
    // //下一首
    // next: function() {
    //     //顺序播放
    //     if(this.index == this.arrSongs.length-1) {
    //         this.index = this.index;

    //     }else{
    //         this.index++;
    //     }
    //     this.player();
    // },
    //时间进度
    time: function(callback) {
        var $this = this;
        $this.audioDom.oncanplaythrough = function() {
            //获取音频的总时长
            var totalTime = this.duration;
            //格式化时长
            var timer = $this.formateTime(totalTime);
            //回调函数 将值进行返回暴露到对象的外面
            if(callback){
                var json = {
                    duration: totalTime,
                    time: timer
                };
                callback.call(json);
            }
        }  
    },
    //格式化时间
    formateTime: function(time) {
        var m = parseInt(time / 60);
        var s = parseInt(time % 60);
        var time = (m<10?("0"+m):m)+":"+(s<10?("0"+s):s);
        return time;
    },
    //音量的控制
    soundCountrol: function(val) {
        this.audioDom.volume = val;
    },
    //播放进度的展示
    percent: function(callback) {
        var $this = this;
        /*
            ontimeupdate: 在视频/音频（audio/video）当前的播放位置发送改变时触发。
            currentTime：返回当前音频的现在时间
            duration：	返回音频的长度（以秒计）。
            floor() 方法执行的是向下取整计算，它返回的是小于或等于函数参数，并且与之最接近的整数。
        */
        $this.audioDom.ontimeupdate = function() {
            //计算播放中的时间进度
            var per = (this.currentTime/this.duration).toFixed(4)*100;
            //总时长减去播放时长  
            var durationtest = $this.formateTime(this.duration);
            var stime = this.duration - this.currentTime;
            //格式化时间
            var timer = $this.formateTime(stime);
            var stimer = $this.formateTime(this.currentTime);
            var json = {
                per : per,
                time : timer,
                stime : stimer,
                durationtest: durationtest
            };
            if(callback) callback.call(json);
        }
    },
    //播放的时间进度
    perTest: function(pre){
        var $this = this;
        $this.audioDom.currentTime = pre;
    },
    //静音
    stopVolome: function() {
        this.audioDom.muted = !this.audioDom.muted;
    },
    //歌词
    songWord: function() {
        var x = e.offset;
    },
    //模式切换
    //控制播放顺序
        //singleLoop:单曲循环
        //listLoop:列表循环
        //randomLoop:随机播放
    
    //播放改变位置
    durong(val){
        this.audioDom.currentTime = val;
    }
};
playerMusic.init();

        //异步同步
        // (async ()=>{
        //     for(var i=0;i<lisLength;i++){
        //         $this.audioDom.src = arrlis[i];
        //         await (()=>{
        //             return new Promise((resolve,reject)=>{
        //                 $this.audioDom.oncanplaythrough = function() {
        //                     var totalTime = this.duration;
        //                     //格式化时长
        //                     var timer = $this.formateTime(totalTime);
        //                     //回调函数 将值进行返回暴露到对象的外面
        //                     var json = {
        //                         duration: totalTime,
        //                         time: timer
        //                     };
        //                     testarr.push(json)
        //                     resolve();
        //                 }
        //             })
        //         })()
        //     }
        //     callback&&callback.call(testarr,testarr)
        // })()


