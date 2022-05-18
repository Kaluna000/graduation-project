let baseUrl = "";
let NODE_ENV='dev';
switch (NODE_ENV) { //注意变量名是自定义的
    //process.env.NODE_ENV
    case 'dev':
        baseUrl = "http://localhost:8888/"  //开发环境url
        break
    case 'serve':
        baseUrl = "http://localhost:8889/"   //生产环境url
        break
}

export default baseUrl;
