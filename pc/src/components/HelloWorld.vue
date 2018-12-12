<template>
  <div class="console">

    <div class="echarts-box">
      <img class="echarts-img" src="./../../static/left.png" alt="">
      <div class="echarts-window" id="box"></div>
      <img class="echarts-img" src="./../../static/right.png" alt="">
    </div>

    <div class="nav">
      <div v-for="(item,index) in buttonData">
        <div
          :class="checkedButton == index?'nav-item-checked':'nav-item'"
          @click="checkButton(item,index)">
          <div class="nav-title">{{item.title}}</div>
          <div class="nav-data">
            {{item.data}}<span class="nav-unit">{{item.unit}}</span>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
  import axios from 'axios'
  import echarts from 'echarts'

  export default {
    name: 'HelloWorld',
    data() {
      return {
        buttonData: [
          {
            title: '环境温度',
            data: 0,
            unit: '℃'
          },
          {
            title: '环境湿度',
            data: 0,
            unit: '%'
          },
          {
            title: 'CO₂',
            data: 0,
            unit: '%'
          },
          {
            title: '光照强度',
            data: 0,
            unit: 'lux'
          },
          {
            title: '土壤温度',
            data: 0,
            unit: '℃'
          },
          {
            title: '土壤湿度',
            data: 0,
            unit: '%'
          },
          {
            title: '土壤PH',
            data: 0,
            unit: ''
          }
        ],
        checkedButton: 0,
      }
    },
    mounted() {
      this.getButtonData()
      this.getEchartsData('airTemperature')
    },
    methods: {
      // 点击切换按钮
      checkButton: function (item, index) {
        console.log(item, index)
        this.checkedButton = index
        switch (index) {
          case 0 :
            this.getEchartsData('airTemperature')
            break
          case 1 :
            this.getEchartsData('airHumidity')
            break
          case 2 :
            this.getEchartsData('CO2')
            break
          case 3 :
            this.getEchartsData('illuminance')
            break
          case 4 :
            this.getEchartsData('soilTemperature')
            break
          case 5 :
            this.getEchartsData('soilHumidity')
            break
          case 6 :
            this.getEchartsData('soilpH')
            break
        }
      },

      // 获取图表数据的接口
      getEchartsData: function (type) {
        axios.get('/api/device/get_data_in_chart', {
          params: {
            id: 504626770,
            type: type
          },
          withCredentials: false
        }).then((res) => {
          console.log(res.data.data)
          let data = res.data.data

          var myChart = echarts.init(document.getElementById("box"));
          var option = {
            // 标题
            title: {
              text: this.buttonData[this.checkedButton].title,
              textStyle: {
                color: '#ffffff',
                fontWeight: 200,
                align: 'center'
              },
              left: '46%',
              top: '5%'
            },
            tooltip: {
              trigger: 'axis',
              backgroundColor: '#1b60ff',
              formatter: '{c}',
            },
            grid: {
              left: '6%',   //图表距边框的距离
              right: '7%',
              bottom: '8%',
              top: '20%',
              containLabel: true
            },
            xAxis: {
              type: 'category',
              boundaryGap: false,
              data: data.time,
              axisLine: {
                lineStyle: {
                  color: '#223873'
                }
              },
              axisTick: {
                show: false
              },
              axisLabel: {
                color: '#a5aabc',
                // showMinLabel:true,
                // showMaxLabel:true
                // rotate:30,
                // interval:0
              },
            },
            yAxis: {
              type: 'value',
              name: '('+this.buttonData[this.checkedButton].unit+')',
              nameGap:20,
              nameTextStyle:{
                color: '#a5aabc',
                left:30
              },
              axisLabel: {
                color: '#a5aabc',
                margin: 21
              },
              axisLine: {
                show: false
              },
              axisTick: {
                show: false
              },
              splitLine: {
                show: true,
                lineStyle: {
                  color: '#223873'
                }
              }
            },
            series: [
              {
                name: '%',
                type: 'line',
                symbol: 'circle',
                // symbol:'image://https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1544613091539&di=c6038785b2b181f9f7f6978876b6e60c&imgtype=0&src=http%3A%2F%2Fimg.lagou.com%2Fthumbnail_600x360%2Fi%2Fimage%2FM00%2F5A%2F64%2FCgqKkVfeMw2ATionAAB5Qpweo2w150.PNG',
                symbolSize: 4,
                areaStyle: {
                  normal: {
                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                      offset: 0,
                      color: '#065f87'
                    }, {
                      offset: 1,
                      color: '#092263'
                    }])
                  }
                },
                markLine: {
                  symbol: '',
                  itemStyle: {
                    lineStyle: {type: 'solid'},
                    label: {show: true, position: 'left', formatter: 'aaa'}
                  },
                  data: [
                    { yAxis: 26,
                      label: {
                        show: true,
                        formatter: '26',
                        color: '#ff4242'
                      },
                      itemStyle: {
                        color: '#853352'
                      }
                    }
                  ]
                },
                itemStyle: {
                  normal: {
                    // color: '#01132f',
                    color: '#00ffff',
                    borderColor: '#00ffff'
                  }
                },
                lineStyle: {
                  color: '#00ffff'
                },
                data: data.data
              }
            ]
          };

          myChart.setOption(option);
        })
      },

      // 获取按钮数据的接口
      getButtonData: function () {
        axios.get('/api/device/get_latest_data', {
          params: {
            id: 504626770
          },
          withCredentials: false
        }).then((res) => {
          console.log(res.data.data)
          let data = res.data.data
          this.buttonData[0].data = data.airTemperature
          this.buttonData[1].data = data.airHumidity
          this.buttonData[2].data = data.CO2
          this.buttonData[3].data = data.illuminance
          this.buttonData[4].data = data.soilTemperature
          this.buttonData[5].data = data.soilHumidity
          this.buttonData[6].data = data.soilpH
        })
      }
    }
  }
</script>

<style scoped>
  .console {
    height: 100vh;
    width: 100vw;
  }

  .echarts-box {
    width: 100vw;
    display: flex;
    justify-content: space-between;
    top: 13vh;
    position: relative;
    align-items: center;
  }

  .echarts-img {
    height: 31vh;
    width: 21vw;
  }

  .echarts-window {
    height: 49vh;
    width: 51vw;
    background: url("./../../static/3.png");
    overflow: auto;
    background-size: 100% 100%;
  }

  .nav {
    display: flex;
    justify-content: space-between;
    position: relative;
    top: 22vh;
    margin: 0 12vw;
    color: #ffffff;
  }

  .nav-item {
    height: 9vw;
    width: 9vw;
    background: url("./../../static/1.png");
    background-size: 100% 100%;
    position: relative;
    cursor: pointer;
  }

  .nav-item-checked {
    height: 9vw;
    width: 9vw;
    background: url("./../../static/2.png");
    background-size: 100% 100%;
    color: #ffaa00;
    position: relative;
    cursor: pointer;
  }

  .nav-title {
    font-size: 0.8vw;
    position: absolute;
    transform: translateX(-50%);
    left: 50%;
    top: 2.7vw;
  }

  .nav-data {
    font-size: 1.5vw;
    position: absolute;
    transform: translateX(-50%);
    left: 50%;
    text-align: center;
    top: 4.3vw;
  }

  .nav-unit {
    font-size: 0.6vw;
  }
</style>
