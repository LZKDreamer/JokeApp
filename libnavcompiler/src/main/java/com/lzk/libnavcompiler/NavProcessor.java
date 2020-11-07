package com.lzk.libnavcompiler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.auto.service.AutoService;
import com.lzk.libnavannotation.ActivityDestination;
import com.lzk.libnavannotation.FragmentDestination;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

/**
 * Author: LiaoZhongKai
 * Date: 2020/11/1 15:39
 * Description:
 */

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({"com.lzk.libnavannotation.ActivityDestination","com.lzk.libnavannotation.FragmentDestination"})
public class NavProcessor extends AbstractProcessor {

    private static final String OUTPUT_FILE_NAME = "destination.json";

    private Messager mMessager;
    private Filer mFiler;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mMessager = processingEnvironment.getMessager();
        mFiler = processingEnvironment.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> fragmentDestinations = roundEnvironment.getElementsAnnotatedWith(FragmentDestination.class);
        Set<? extends Element> activityDestinations = roundEnvironment.getElementsAnnotatedWith(ActivityDestination.class);
        if (!fragmentDestinations.isEmpty() || !activityDestinations.isEmpty()){
            HashMap<String, JSONObject> destMaps = new HashMap<>();
            handleDestinations(fragmentDestinations,FragmentDestination.class,destMaps);
            handleDestinations(activityDestinations,ActivityDestination.class,destMaps);

            FileOutputStream fos = null;
            OutputStreamWriter writer = null;
            try {
                FileObject resource = mFiler.createResource(StandardLocation.CLASS_OUTPUT,"",OUTPUT_FILE_NAME);
                String resourcePath = resource.toUri().getPath();
                mMessager.printMessage(Diagnostic.Kind.NOTE, "resourcePath:" + resourcePath);
                String appPath = resourcePath.substring(0,resourcePath.indexOf("app")+4);
                String assetsPath = appPath + "src/main/assets/";

                File file = new File(assetsPath);
                if (!file.exists()) {
                    file.mkdirs();
                }
                File outPutFile = new File(file, OUTPUT_FILE_NAME);
                if (outPutFile.exists()) {
                    outPutFile.delete();
                }
                outPutFile.createNewFile();
                String content = JSON.toJSONString(destMaps);
                fos = new FileOutputStream(outPutFile);
                writer = new OutputStreamWriter(fos, "UTF-8");
                writer.write(content);
                writer.flush();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if (fos != null){
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (writer != null){
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return true;
    }

    private void handleDestinations(Set<? extends Element> elements, Class<? extends Annotation> annotationClz, HashMap<String, JSONObject> destMaps) {
        for (Element element : elements){
            TypeElement typeElement = (TypeElement) element;

            String pageUrl = null;
            String clazzName = typeElement.getQualifiedName().toString();
            int id = Math.abs(clazzName.hashCode());
            boolean needLogin = false;
            boolean asStarter = false;
            boolean isFragment = false;

            Annotation annotation = typeElement.getAnnotation(annotationClz);
            if (annotation instanceof FragmentDestination){
                FragmentDestination fragmentDestination = (FragmentDestination) annotation;
                pageUrl = fragmentDestination.pageUrl();
                needLogin = fragmentDestination.needLogin();
                asStarter = fragmentDestination.asStarter();
                isFragment = true;
            }else if (annotation instanceof ActivityDestination){
                ActivityDestination activityDestination = (ActivityDestination) annotation;
                pageUrl = activityDestination.pageUrl();
                needLogin = activityDestination.needLogin();
                asStarter = activityDestination.asStarter();
                isFragment = false;
            }

            if (destMaps.containsKey(pageUrl)){
                mMessager.printMessage(Diagnostic.Kind.ERROR,"不同的页面不能使用相同的page url");
            }else {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id",id);
                jsonObject.put("pageUrl",pageUrl);
                jsonObject.put("clazzName",clazzName);
                jsonObject.put("needLogin",needLogin);
                jsonObject.put("asStarter",asStarter);
                jsonObject.put("isFragment",isFragment);
                destMaps.put(pageUrl,jsonObject);
            }
        }
    }
}
