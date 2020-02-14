package impl.image;

import api.image.ConvolutionProvider;

import java.awt.*;
import java.lang.Math.*;
public class ConvolutionProviderFactory {
    private static ConvolutionProviderFactory instance;
    public static ConvolutionProviderFactory getInstance()
    {
        if (instance == null)instance = new ConvolutionProviderFactory();
        return instance;
    }
    public Color[][] apply(Color[][] image, double[][] kernel){
        int n=image.length,m=image[0].length,kn =kernel.length,km=kernel[0].length;
        Color[][] img = new Color[n][m];
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                int blue=0,red=0,green=0;
                int id1=0,id2 =0;
                for (int it1=-(kn/2);it1<=(kn/2);it1++){
                    id2=0;
                    for(int it2=-(km/2);it2<=(km/2);it2++) {
                        if (j + it2 < 0 || i + it1 < 0 || j + it2 >= m || i + it1 >= n) {id2+=1;continue;}
                        blue += (int)(kernel[id1][id2] * image[i + it1][j + it2].getBlue());
                        green += (int)(kernel[id1][id2] * image[i + it1][j + it2].getGreen());
                        red += (int)(kernel[id1][id2] * image[i + it1][j + it2].getRed());
                        id2 += 1;
                    }
                    id1+=1;
                }
                img[i][j]=new Color((int)(red),(int)green,(int)blue,255);;
            }
        }



       return img;
    }


}