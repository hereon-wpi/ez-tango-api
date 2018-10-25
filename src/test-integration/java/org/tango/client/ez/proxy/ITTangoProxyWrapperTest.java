// +======================================================================
//   $Source$
//
//   Project:   ezTangORB
//
//   Description:  java source code for the simplified TangORB API.
//
//   $Author: Igor Khokhriakov <igor.khokhriakov@hzg.de> $
//
//   Copyright (C) :      2014
//                        Helmholtz-Zentrum Geesthacht
//                        Max-Planck-Strasse, 1, Geesthacht 21502
//                        GERMANY
//                        http://hzg.de
//
//   This file is part of Tango.
//
//   Tango is free software: you can redistribute it and/or modify
//   it under the terms of the GNU Lesser General Public License as published by
//   the Free Software Foundation, either version 3 of the License, or
//   (at your option) any later version.
//
//   Tango is distributed in the hope that it will be useful,
//   but WITHOUT ANY WARRANTY; without even the implied warranty of
//   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//   GNU Lesser General Public License for more details.
//
//   You should have received a copy of the GNU Lesser General Public License
//   along with Tango.  If not, see <http://www.gnu.org/licenses/>.
//
//  $Revision: 25721 $
//
// -======================================================================

package org.tango.client.ez.proxy;

import fr.esrf.Tango.DevState;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.tango.client.ez.data.type.TangoImage;
import org.tango.client.ez.util.TangoImageUtils;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import static junit.framework.Assert.*;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertSame;

/**
 * Some tests performs similar operation many times. It is just to make sure that it does not have any side effects.
 *
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 07.06.12
 */
public class ITTangoProxyWrapperTest {
    //must be the same as in pom.xml
    public static final String TANGO_DEV_NAME = "test/local/0";
    public static final int TANGO_PORT = 16547;


    public static final String TEST_TANGO = "tango://localhost:" + TANGO_PORT + "/" + TANGO_DEV_NAME + "#dbase=no";

    private TangoProxy instance;

    @Before
    public void before() throws Exception {
        instance = new DeviceProxyWrapper(TEST_TANGO);
    }

    @Test(expected = NoSuchAttributeException.class)
    public void testReadAttribute_Failed() throws Exception {
        instance.readAttribute("string_scalarxxx");//no such attribute
    }

    //@Test
    public void testReadAttribute_Exception() throws Exception {
        instance.readAttribute("string_scalar");


        instance.readAttribute("string_scalar");
    }

    @Test
    public void testWriteReadAttribute_String() throws Exception {
        instance.writeAttribute("string_scalar", "Some test value");
        instance.writeAttribute("string_scalar", "Some test value");
        instance.writeAttribute("string_scalar", "Some test value");
        instance.writeAttribute("string_scalar", "Some test value");

        instance.readAttribute("string_scalar");
        instance.readAttribute("string_scalar");
        instance.readAttribute("string_scalar");
        instance.readAttribute("string_scalar");
        instance.readAttribute("string_scalar");
        String result = instance.readAttribute("string_scalar");

        assertEquals("Some test value", result);
    }

    @Test
    public void testWriteReadAttribute_Double() throws Exception {
        instance.writeAttribute("double_scalar_w", 0.1984D);
        instance.writeAttribute("double_scalar_w", 0.1984D);
        instance.writeAttribute("double_scalar_w", 0.1984D);

        instance.<Double>readAttribute("double_scalar_w");
        double result = instance.<Double>readAttribute("double_scalar_w");

        assertEquals(0.1984D, result);
    }

    @Test
    public void testWriteReadAttribute_DoubleSpectrum() throws Exception {
        instance.writeAttribute("double_spectrum", new double[]{0.1D, 0.2D, 0.3D, 0.4D});
        double[] result = instance.<double[]>readAttribute("double_spectrum");

        assertEquals(4, result.length);
        assertArrayEquals(new double[]{0.1D, 0.2D, 0.3D, 0.4D}, result, 0.07D);
    }

    @Test
    public void testWriteReadAttribute_ULongScalar() throws Exception {
        instance.writeAttribute("ulong_scalar", 1234L);
        long result = instance.<Long>readAttribute("ulong_scalar");

        //TangoTest returns random number here
        //assume that test has passed if not failed
        //assertEquals(1234L, result);
    }

    //WAttribute::check_written_value():API_IncompatibleAttrDataType(Incompatible attribute type, expected type is : Tango::DevVarCharArray (even for single value))
    @Test
    public void testWriteReadAttribute_UChar() throws Exception {
        instance.writeAttribute("uchar_scalar", 'a');
        instance.writeAttribute("uchar_scalar", 'a');
        instance.writeAttribute("uchar_scalar", 'a');

        instance.readAttribute("uchar_scalar");
        char result = instance.<Character>readAttribute("uchar_scalar");

        //TODO TangoTest does not write uchar
        //assertEquals('a', result);
    }

    @Test
    public void testWriteReadAttribute_DoubleArr() throws Exception {
        instance.writeAttribute("double_spectrum", new double[]{0.1D, 0.9D, 0.8D, 0.4D});
        instance.writeAttribute("double_spectrum", new double[]{0.1D, 0.9D, 0.8D, 0.4D});
        instance.writeAttribute("double_spectrum", new double[]{0.1D, 0.9D, 0.8D, 0.4D});
        instance.writeAttribute("double_spectrum", new double[]{0.1D, 0.9D, 0.8D, 0.4D});
        instance.writeAttribute("double_spectrum", new double[]{0.1D, 0.9D, 0.8D, 0.4D});

        instance.readAttribute("double_spectrum");
        instance.readAttribute("double_spectrum");
        double[] result = instance.readAttribute("double_spectrum");

        assertArrayEquals(new double[]{0.1D, 0.9D, 0.8D, 0.4D}, result, 0.0);
    }

    @Test
    public void testWriteReadAttribute_DoubleArrArr() throws Exception {
        double[][] value = {{0.1D, 0.4D}, {0.9D, 0.8D}, {0.8D, 0.9D}, {0.4D, 0.1D}};
        TangoImage<double[]> image_in = TangoImage.from2DArray(value);
        instance.writeAttribute("double_image", image_in);
        instance.writeAttribute("double_image", image_in);
        instance.writeAttribute("double_image", image_in);
        instance.writeAttribute("double_image", image_in);
        instance.writeAttribute("double_image", image_in);

        instance.readAttribute("double_image");
        instance.readAttribute("double_image");
        instance.readAttribute("double_image");
        TangoImage<double[]> image_out = instance.readAttribute("double_image");
        double[][] result = image_out.to2DArray();

        assertArrayEquals(new double[]{0.1D, 0.4D}, result[0], 0.0);
        assertArrayEquals(new double[]{0.9D, 0.8D}, result[1], 0.0);
        assertArrayEquals(new double[]{0.8D, 0.9D}, result[2], 0.0);
        assertArrayEquals(new double[]{0.4D, 0.1D}, result[3], 0.0);
    }

//    @Test
    public void testReadImage_fromWebCam() throws Exception{
        TangoImage<int[]> image = instance.readAttribute("image");

        RenderedImage renderedImage = TangoImageUtils.toRenderedImage_sRGB(image.getData(), image.getWidth(), image.getHeight());
        ImageIO.write(renderedImage, "JPEG", Files.createTempFile("testReadImage", ".jpeg").toFile());
    }

    @Test
    public void testReadImage_sRGB() throws Exception{
        TangoImage<int[]> image = instance.readAttribute("ushort_image_ro");


        RenderedImage renderedImage = TangoImageUtils.toRenderedImage_sRGB(image.getData(), image.getWidth(), image.getHeight());
        assertTrue(ImageIO.write(renderedImage, "JPEG", Files.createTempFile("testReadImage_",".jpeg").toFile()));
    }

    //@Test
    public void testReadImage_GRAY() throws Exception{
        TangoImage<float[]> image = instance.readAttribute("float_image_ro");


        RenderedImage renderedImage = TangoImageUtils.toRenderedImageDedicatedComponents_GRAY(image.getData(), image.getWidth(), image.getHeight());
        assertTrue(ImageIO.write(renderedImage, "TIF", Files.createTempFile("testReadImage_", ".tiff").toFile()));
    }

    //@Test
    public void testReadImage_GRAY0() throws Exception{
        TangoProxy instance = new DeviceProxyWrapper("development/test/0");
        TangoImage<int[]> image = instance.readAttribute("image");


        RenderedImage renderedImage = TangoImageUtils.toRenderedImageDedicatedComponents_GRAY(image.getData(), image.getWidth(), image.getHeight());
        assertTrue(ImageIO.write(renderedImage, "TIF", Files.createTempFile("testReadImage_", ".tiff").toFile()));
    }

    @Test
    public void testSubscription() throws Exception{
        instance.toDeviceProxy().poll_attribute("long_scalar", 100);


        final CountDownLatch done = new CountDownLatch(1);
        final AtomicBoolean success = new AtomicBoolean();

        instance.subscribeToEvent("long_scalar", TangoEvent.CHANGE);
        instance.addEventListener("long_scalar", TangoEvent.CHANGE, new TangoEventListener<Long>() {
            @Override
            public void onEvent(EventData<Long> data) {
                System.out.println(data.getValue());
                success.set(true);
                done.countDown();
            }

            @Override
            public void onError(Exception cause) {
                cause.printStackTrace();
                //TODO
                success.set(true);
                done.countDown();
            }
        });

        done.await();
        assertTrue(success.get());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWriteReadAttribute_DoubleArrArr_Failed() throws Exception {
        TangoImage<double[]> image = TangoImage.from2DArray(new double[][]{{0.1D, 0.4D}, {0.9D}});

        instance.writeAttribute("double_image", image);
    }

    @Test(expected = TangoProxyException.class)
    public void testWriteReadAttribute_DoubleArrArr_TooBig() throws Exception {
        instance.writeAttribute("double_image", new TangoImage<double[]>(new double[256*256],256,256));//251 max
    }

    @Test
    public void testSubscribeEvent() throws Exception {
        //TODO
    }

    @Test
    public void testUnsubscribeEvent() throws Exception {
        //TODO
    }

    @Test
    public void testExecuteCommand_String() throws Exception {
        String result = instance.executeCommand("DevString", "Some test value");

        assertEquals("Some test value", result);
    }

    @Test
    public void testExecuteCommand_Void() throws Exception {
        Void result = instance.executeCommand("DevVoid", null);

        assertNull(result);
    }

    @Test
    public void testExecuteCommand_DblArr() throws Exception {
        double[] result = instance.executeCommand("DevVarDoubleArray", new double[]{0.1D, 0.9D, 0.8D, 0.4D});

        assertArrayEquals(new double[]{0.1D, 0.9D, 0.8D, 0.4D}, result, 0.0);
    }

    @Test
    public void testExecuteCommand_FltArr() throws Exception {
        float[] result = instance.executeCommand("DevVarFloatArray", new float[]{0.1F, 0.9F, 0.8F, 0.4F});

        assertArrayEquals(new float[]{0.1F, 0.9F, 0.8F, 0.4F}, result, 0.0F);
    }

    @Test
    public void testWriteUShort() throws Exception {
        instance.writeAttribute("ushort_scalar", 4);

    }

    @Test
    public void testReadState() throws Exception {
        DevState result = instance.readAttribute("State");
        assertSame(DevState.RUNNING, result);
    }

}
