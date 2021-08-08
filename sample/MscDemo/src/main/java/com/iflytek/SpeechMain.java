package com.iflytek;

import com.iflytek.cloud.b.b.c;
import com.iflytek.cloud.b.b.d;
import com.iflytek.cloud.speech.*;
import com.iflytek.util.DebugLog;
import com.iflytek.view.IatSpeechView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: ...
 * @Author: Robin-Li
 * @DateTime: 2021-08-01 17:27
 */
public class SpeechMain {

    public static void main(String[] args) throws InterruptedException {

        SpeechMain main = new SpeechMain();
        main.initParamMap();

        SpeechUtility.createUtility( SpeechConstant.APPID +"=0d281bfd ");

        // 初始化听写对象
        SpeechRecognizer mIat = SpeechRecognizer.createRecognizer();
        main.setting(mIat);
        mIat.startListening(recognizerListener);
        Thread.sleep(20000);
        mIat.startListening(null);
    }

    public static void loadLibrary(String libName_32, String libName_64) {
        String path = System.getProperty("user.dir");
        if (c.a(path)) {
            path = ".";
        }

        String key = "java.library.path";
        System.setProperty(key, System.getProperty(key) + File.pathSeparator + path);

        //b.if("path:" + System.getProperty("java.library.path"));
        String os_version = System.getProperty("sun.arch.data.model");

        try {
            if (!c.a(os_version) && "64".equals(os_version)) {
                System.loadLibrary(libName_64);
            } else {
                System.loadLibrary(libName_32);
            }

        } catch (UnsatisfiedLinkError var5) {
        }
    }


    /**
     * 听写监听器
     */
    private static RecognizerListener recognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {
            DebugLog.Log("onBeginOfSpeech enter");
        }

        @Override
        public void onEndOfSpeech() {
            DebugLog.Log("onEndOfSpeech enter");
        }

        /**
         * 获取听写结果. 获取RecognizerResult类型的识别结果，并对结果进行累加，显示到Area里
         */
        @Override
        public void onResult(RecognizerResult results, boolean islast) {
            DebugLog.Log("onResult enter");

            //如果要解析json结果，请考本项目示例的 com.iflytek.util.JsonParser类
//			String text = JsonParser.parseIatResult(results.getResultString());
            String text = results.getResultString();
            System.out.println("out=" + text);
        }

        @Override
        public void onVolumeChanged(int volume) {
            DebugLog.Log("onVolumeChanged enter");
        }

        @Override
        public void onError(SpeechError error) {
            DebugLog.Log("onError enter");
            if (null != error) {
                DebugLog.Log("onError Code：" + error.getErrorCode());
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int agr2, String msg) {
            DebugLog.Log("onEvent enter");
            //以下代码用于调试，如果出现问题可以将sid提供给讯飞开发者，用于问题定位排查
			/*if(eventType == SpeechEvent.EVENT_SESSION_ID) {
				DebugLog.Log("sid=="+msg);
			}*/
        }
    };

    void setting(SpeechRecognizer mIat) {
        final String engType = this.mParamMap.get(SpeechConstant.ENGINE_TYPE);

        for (Map.Entry<String, String> entry : this.mParamMap.entrySet()) {
            mIat.setParameter(entry.getKey(), entry.getValue());
        }

        //本地识别时设置资源，并启动引擎
        if (SpeechConstant.TYPE_LOCAL.equals(engType)) {
            //启动合成引擎
            mIat.setParameter(ResourceUtil.ENGINE_START, SpeechConstant.ENG_ASR);

            //设置资源路径
            final String rate = this.mParamMap.get(SpeechConstant.SAMPLE_RATE);
            final String tag = rate.equals("16000") ? "16k" : "8k";
            String curPath = System.getProperty("user.dir");
            DebugLog.Log("Current path=" + curPath);
            String resPath = ResourceUtil.generateResourcePath(curPath + "/asr/common.jet")
                    + ";" + ResourceUtil.generateResourcePath(curPath + "/asr/src_" + tag + ".jet");
            System.out.println("resPath=" + resPath);
            mIat.setParameter(ResourceUtil.ASR_RES_PATH, resPath);
        }// end of if is TYPE_LOCAL

    }// end of function setting


    private Map<String, String> mParamMap = new HashMap<String, String>();

    private void initParamMap() {
        this.mParamMap.put(SpeechConstant.ENGINE_TYPE, IatSpeechView.DefaultValue.ENG_TYPE);
        this.mParamMap.put(SpeechConstant.SAMPLE_RATE, IatSpeechView.DefaultValue.RATE);
        this.mParamMap.put(SpeechConstant.NET_TIMEOUT, IatSpeechView.DefaultValue.NET_TIMEOUT);
        this.mParamMap.put(SpeechConstant.KEY_SPEECH_TIMEOUT, IatSpeechView.DefaultValue.SPEECH_TIMEOUT);

        this.mParamMap.put(SpeechConstant.LANGUAGE, IatSpeechView.DefaultValue.LANGUAGE);
        this.mParamMap.put(SpeechConstant.ACCENT, IatSpeechView.DefaultValue.ACCENT);
        this.mParamMap.put(SpeechConstant.DOMAIN, IatSpeechView.DefaultValue.DOMAIN);
        this.mParamMap.put(SpeechConstant.VAD_BOS, IatSpeechView.DefaultValue.VAD_BOS);

        this.mParamMap.put(SpeechConstant.VAD_EOS, IatSpeechView.DefaultValue.VAD_EOS);
        this.mParamMap.put(SpeechConstant.ASR_NBEST, IatSpeechView.DefaultValue.NBEST);
        this.mParamMap.put(SpeechConstant.ASR_WBEST, IatSpeechView.DefaultValue.WBEST);
        this.mParamMap.put(SpeechConstant.ASR_PTT, IatSpeechView.DefaultValue.PTT);

        this.mParamMap.put(SpeechConstant.RESULT_TYPE, IatSpeechView.DefaultValue.RESULT_TYPE);
        this.mParamMap.put(SpeechConstant.ASR_AUDIO_PATH, null);
    }
}
