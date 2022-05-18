<template>
  <div id="terminal" style="height:100vh"></div>
</template>

<script>
import { Terminal } from "xterm";
import { FitAddon } from 'xterm-addon-fit';
import "xterm/css/xterm.css";
import "xterm/lib/xterm.js";

export default {
  name: "Xterminal2",
  data(){
    return {
      connectData:{
        operate:'connect',
        host:'101.132.194.46',
        // host:'139.224.189.192',
        port:this.$store.state.nowNodePort,
        username: 'root',//用户名
        password: 'LuoRan2000',
        cols: '',
        rows: ''
      },
      websock: '',
      fitAddon:'',
      term:''
    }
  },
  beforeDestroy() {
    this.websock.close()
    this.term.dispose()
  },
  mounted() {
    const terminal = document.getElementById("terminal");
    this.term = new Terminal({
      cursorBlink: true, // 光标闪烁
      cursorStyle: "block", // 光标样式  null | 'block' | 'underline' | 'bar'
      scrollback: 800, //回滚
      tabStopWidth: 8, //制表宽度
      screenKeys: true
    });

    this.fitAddon = new FitAddon();
    this.term.loadAddon(this.fitAddon);
    this.term.open(terminal);
    this.fitAddon.fit();
    this.connectData.cols = this.term.cols;
    this.connectData.rows = this.term.rows;

    this.initWebSocket();
    window.addEventListener("resize", () => {
      if(this.websock.readyState == 1){
        this.fitAddon.fit();
        this.websocketsend(JSON.stringify({"operate": "resize", "cols": this.term.cols, "rows": this.term.rows}));
      }
    });
    this.term.onData((data) => {
      //键盘输入时的回调函数
      this.websocketsend(JSON.stringify({"operate": "command", "command": data}));
    });
    this.term.write('Connecting...\r\n');
  },
  methods:{
    initWebSocket(){ //初始化weosocket
      const wsuri = "ws://localhost:8888/webssh";
      this.websock = new WebSocket(wsuri);
      this.websock.onmessage = this.websocketonmessage;
      this.websock.onopen = this.websocketonopen;
      this.websock.onerror = this.websocketonerror;
      this.websock.onclose = this.websocketclose;
    },
    websocketonopen(){ //连接建立之后执行send方法发送数据
      this.websocketsend(JSON.stringify(this.connectData));
    },
    websocketonerror(error){//连接建立失败重连
      this.term.write('Error: ' + error + '\r\n');
    },
    websocketonmessage(e){ //数据接收
      if(e.data.indexOf('logout') != -1){
        this.term.dispose();
        this.websock.close();
      }else if(e.data.indexOf('401') != -1){
        alert(e.data)
        this.term.dispose();
        this.websock.close();
      }else{
        this.term.write(e.data);
      }
    },
    websocketsend(data){//数据发送
      this.websock.send(data);
    },
    websocketclose(e){  //关闭
      console.log('断开连接',e);
      this.term.dispose();
    }
  }
}
</script>

<style>

</style>