package org.angzangy.aalive;

import android.opengl.GLES20;

/**
 * Created on 2017/3/20.
 */

public class MosaicSurfaceTextureRenderer extends SurfaceTextureRenderer {
    private int mImageSizeHandle;
    public final  static String OES_MosaicFragmentShader =
            "#extension GL_OES_EGL_image_external : require\n" +
                    "precision mediump float;\n" +
                    "varying vec2 vTextureCoord;\n" +
                    "uniform samplerExternalOES sTexture;\n" +
                    "uniform vec2 vImgSize;\n"+
                    "const float numberOfMosaic = 8.0;\n"+
                    "void main() {\n" +

                    "vec2 imgCoord = vTextureCoord * vImgSize;\n"+
                    "vec2 mosaicCoord = floor(imgCoord/numberOfMosaic)*numberOfMosaic;\n"+
                    "  gl_FragColor = texture2D(sTexture, mosaicCoord/vImgSize);\n" +
                    "}\n";

    public void loadShader(String vertexShader, String fragmentShader) {
        super.loadShader(vertexShader, fragmentShader);
        mImageSizeHandle = GLES20.glGetUniformLocation(mProgram, "vImgSize");
        if(mImageSizeHandle == -1){
            throw new RuntimeException("Could not get uniform location for vImgSize");
        }
    }

    protected void setRendererParamaters(float[]mvpMatrix, float[]stMatrix){
        super.setRendererParamaters(mvpMatrix, stMatrix);
        GLES20.glUniform2f(mImageSizeHandle, 640, 480);
    }
}
