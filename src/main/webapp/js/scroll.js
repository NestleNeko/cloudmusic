function scrollDom(boxDom,preDom,precessDom){
    this.boxDom = document.getElementById(boxDom);
    this.preDom = document.getElementById(preDom);
    this.precessDom = document.getElementById(precessDom);
}
//scrollDom 继承obj.prototype  对scrollDom对象进行方法扩展 动态扩展
scrollDom.prototype = {
    init: function(onmousemovecallback,moveupcallback){
        var max = this.precessDom.offsetWidth - document.getElementById("circlebox").offsetWidth;
        var $this = this;
        var x=0,y=0;
        var boxDomWidth,preDomLeft;
        //鼠标按键被按下时发生的事件
        $this.preDom.onmousedown = function(e) {
            var domy = e.clientX;//返回事件被触发的时候，鼠标指针的水平坐标
            var left = this.offsetLeft;//初始的left值
            var boxDomWidth,preDomLeft;
            // onmousemove	鼠标被移动。
            document.onmousemove = function(e) {
                var thisX = e.clientX;//clientX	返回当事件被触发时，鼠标指针的水平坐标。boxDom
                var nl = thisX - domy + left;
                if(nl <= 0){
                    $this.ondrag(0)
                }else if(nl >= max){
                    $this.ondrag(max);
                }else{
                    $this.ondrag(nl,function(){
                        x = this.testx;
                        y = this.testy;
                        var json = {
                            "x": x,
                            "y": y
                        }
                        onmousemovecallback&&onmousemovecallback(json);
                    })
                }
                
            };
            //onmouseup	鼠标按键被松开。
            document.onmouseup = function() {
                document.onmousemove = null;
                document.onmouseup = null;
                preDomLeft = x ;
                boxDomWidth = y;
                var json = {
                    "x": preDomLeft,
                    "y": boxDomWidth
                }
                moveupcallback&&moveupcallback(json);
            } 
        }
    },
    ondrag: function(x,callback){
        this.boxDom.style.width = x + "px";
        this.preDom.style.left = x + "px";
        if(callback){
            var json = {
                "testx": this.boxDom.style.width,
                "testy": this.preDom.style.left
            }
            callback.call(json);
        }
    }

}