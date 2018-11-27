//存放主要交互逻辑代码
var seckill = {
    //封装秒杀相关URL
    URL: {
        now: function () {
            return '/seckill/time/now';
        },
        exposer: function (seckillId) {
            return '/seckill/' + seckillId + "/exposer";
        },
        execution: function (seckillId, md5) {
            return '/seckill/' + seckillId + '/' + md5 + '/execution';
        }
    },
    //验证手机号
    validatePhone: function (phone) {
        //进行判断手机是否存在，手机号长度，是否是一个数字
        if (phone && phone.length == 11 && !isNaN(phone)) {
            return true;
        } else {
            return false;
        }
    },
    handleSeckill: function (seckillId, node) {
        //處理秒殺邏輯
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">開始秒殺</button>');
        $.post(seckill.URL.exposer(seckillId), {},
            function (result) {
            if (result && result['success']) {
                var exposer = result['data'];
                if (exposer['exposed']) {
                    //開啟秒殺
                    var md5 = exposer['md5'];
                    var killUrl = seckill.URL.execution(seckillId, md5);
                    console.log("killURL:", killUrl);
                    //  綁定一次點擊事件
                    $('#killBtn').one('click', function () {
                        //執行秒殺請求的操作
                        $(this).addClass('disabled');
                        $.post(killUrl, {}, function (result) {
                            if (result && result['success']) {
                                var killResult = result['data'];
                                var state = killResult['state'];
                                var stateInfo = killResult['stateInfo'];
                                //顯示秒殺結果
                                node.html('<span class="label label-success">' + stateInfo + '</span>')
                            }
                        });
                    });
                    node.show();
                } else {
                    //未開啟秒殺
                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];
                    //重新計算計時邏輯
                    seckill.countdown(seckillId, now, start, end);

                }
            } else {
                console.log("handleSeckill-result:", result);
            }
        });
    },
    countdown: function (seckillId, nowTime, startTime, endTime) {
        var seckillBox = $('#seckill-box');
        //比较时间
        if (nowTime > endTime) {
            //秒杀结束
            seckillBox.html('秒杀结束');
        } else if (nowTime < startTime) {
            //秒杀未开始 开始计时
            var killTime = new Date(startTime + 1000);
            seckillBox.countdown(killTime, function (event) {
                var format = event.strftime('秒杀倒计时: %D天 %H时 %M分 %S秒');
                seckillBox.html(format);
            }).on('finish.countdown', function () {
                //穫取秒殺地址，控制顯示邏輯，執行秒殺
                seckill.handleSeckill(seckillId, seckillBox);
            });
        } else {
            //秒殺開始
            seckill.handleSeckill(seckillId,seckillBox);
        }
    },
    //详情页秒杀相关逻辑
    detail: {
        //详情页初始化
        init: function (params) {
            console.log("no error");
            //手机验证和登录，计时交互
            //在cookie中查找手机号
            var userPhone = $.cookie("userPhone");

            //验证手机号
            if (!seckill.validatePhone(userPhone)) {
                //绑定phone
                //控制输出
                var userPhoneModal = $("#userPhoneModal");
                userPhoneModal.modal({
                    show: true,//显示弹出窗
                    backdrop: 'static',
                    keyboard: false
                });
                $('#userPhoneBtn').click(function () {
                    var inputPhone = $('#userPhoneKey').val();
                    if (seckill.validatePhone(inputPhone)) {
                        //写入电话到cookie并刷星页面，并指定cookie的生效时间和生效url
                        $.cookie('userPhone', inputPhone, {expires: 7, path: '/seckill'});
                        window.location.reload();
                    } else {
                        $('#userPhoneMessage').hide().html('<label class="label label-danger">手机号错误</label>').show(300);
                    }
                });
            }
            //已经登录，进行计时交互
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];
            $.get(
                seckill.URL.now(),
                {},
                function (result) {
                    if (result && result['success']) {
                        var nowTime = result['data'];
                        console.log("nowTime:", nowTime);
                        seckill.countdown(seckillId, nowTime, startTime, endTime);
                    } else {
                        console.log("result:", result);
                    }
                });
        }//end init
    }//end detail
};