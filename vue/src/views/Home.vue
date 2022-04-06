<template>
    <div>
        <div class="echart" id="mychart" :style="myChartStyle"></div>
        <div class="proCharts" ref="Homecharts"></div>
    </div>
</template>

<script>
    import * as echarts from "echarts";

    export default {
        data() {
            return {
                myChart: {},
                pieData: [
                    {
                        value: 463,
                        name: "江苏"
                    },
                    {
                        value: 395,
                        name: "浙江"
                    },
                    {
                        value: 157,
                        name: "山东"
                    },
                    {
                        value: 149,
                        name: "广东"
                    },
                    {
                        value: 147,
                        name: "湖南"
                    }
                ],
                pieName: [],
                myChartStyle: { float: "left", width: "50%", height: "400px"}, //图表样式

                option:{
                    color:['rgb(8,252,7)','rgb(255,168,0)',],
                    title: {
                        text: '一个线状图表',
                        color:'#fff',
                    },
                    tooltip: { //提示框
                        trigger: 'axis',
                    },
                    legend: {//图例的类型
                        icon:'roundRect',//图例icon图标
                        data: [
                            {
                                name:"上年",
                                textStyle: {
                                    color: '#fff'
                                }

                            },{
                                name:"本年",
                                textStyle: {
                                    color: '#fff'
                                }
                            },
                        ],

                    },
                    grid: {
                        left: '3%',
                        right: '5%',
                        bottom: '3%',
                        top:'17%',
                        containLabel: true //grid区域是否包含坐标轴的刻度标签
                    },
                    xAxis: {
                        type: 'category', //坐标轴类型。
                        boundaryGap: true, //坐标轴两边留白策略
                        data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月','8月','9月','10月','11月','12月'],
                        axisLabel: {//坐标轴刻度标签的相关设置
                            interval:0,
                            textStyle: {
                                color: '#fff',
                                fontSize :10
                            },
                        },
                        axisLine:{//坐标轴轴线相关设置
                            show :true,
                            lineStyle:{
                                color:'rgb(2,121,253)'
                            }
                        },
                        axisTick:{ //坐标轴刻度相关设置。
                            show :false,
                        }
                    },
                    yAxis: {
                        type: 'value',
                        axisLabel: { //x轴的坐标文字
                            show: true,
                            textStyle: {
                                color: '#fff' //文字的颜色
                            },

                        },
                        max:100,//最大值100
                        axisLine:{//坐标轴轴线相关设置
                            show :true,
                            lineStyle:{
                                color:'rgb(2,121,253)'
                            }
                        },
                        axisTick:{ //坐标轴刻度相关设置。
                            show :true,
                        },
                        splitLine:{  //坐标在grid区域的分割线
                            lineStyle: { //设置分割线的样式(图表横线颜色)
                                color: ['#153a8a']
                            }
                        }
                    },
                    series: [
                        {
                            name: '上年',
                            type: 'line',
                            data: [10,20,30,50,50,10,50,60,10,50,10,30],
                            lineStyle:{
                                color:'rgb(8,252,7)'  //线的颜色
                            }
                        },
                        {
                            name: '本年',
                            type: 'line',
                            data: [20,20,30,50,50,10,50,20,30,50,50,30],
                            lineStyle:{
                                color:'rgb(255,168,0)' //线的颜色
                            }
                        }
                    ]
                }
            };
        },
        created() {
        },
        mounted() {
            this.initDate(); //数据初始化
            this.initEcharts();
            this.HomemyChart()
        },
        methods: {
            HomemyChart(){
                let HomemyChart =echarts.init(this.$refs.Homecharts,"macarons");
                HomemyChart.setOption(this.option)
                //图表自适应
                window.addEventListener("resize",function(){
                    HomemyChart.resize()  // myChart 是实例对象
                })
            },



            initDate() {
                for (let i = 0; i < this.pieData.length; i++) {
                    // this.xData[i] = i;
                    // this.yData =this.xData[i]*this.xData[i];
                    this.pieName[i] = this.pieData[i].name;
                }
            },
            initEcharts() {
                // 饼图
                const option = {
                    legend: {
                        // 图例
                        data: this.pieName,
                        right: "10%",
                        top: "30%",
                        orient: "vertical"
                    },
                    title: {
                        // 设置饼图标题，位置设为顶部居中
                        text: "一个饼状图表",
                        top: "0%",
                        left: "center"
                    },
                    series: [
                        {
                            type: "pie",
                            label: {
                                show: true,
                                formatter: "{b} : {c} ({d}%)" // b代表名称，c代表对应值，d代表百分比
                            },
                            radius: "30%", //饼图半径
                            data: this.pieData
                        }
                    ]
                };
                console.log(this.seriesData);
                const optionFree = {
                    xAxis: {},
                    yAxis: {},
                    series: [
                        {
                            data: this.seriesData,
                            type: "line",
                            smooth: true
                        }
                    ]
                };
                this.myChart = echarts.init(document.getElementById("mychart"));
                this.myChart.setOption(option);
                //随着屏幕大小调节图表
                window.addEventListener("resize", () => {
                    this.myChart.resize();
                });
            },
        }
    };
</script>
<style scoped>
    .proCharts{
        left: 50%;
        width: 50%;
        height: 400px;
        background: rgb(14, 51, 129);
    }
</style>

