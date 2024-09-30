package com.charleslgn.intellitwitch.ui

import org.mockito.Mockito
import java.awt.Graphics
import javax.swing.ImageIcon
import kotlin.test.Test
import kotlin.test.assertEquals

class TestMultipleImageIcon {

    @Test
    fun test() {
        // Given
        val redImage = ImageIcon({}::class.java.classLoader.getResource("red.png"))
        val blueImage = ImageIcon({}::class.java.classLoader.getResource("blue.png"))
        val g = Mockito.mock(Graphics::class.java)
        // When
        val multipleImageIcon = MultipleImageIcon(listOf(redImage, blueImage))
        multipleImageIcon.paintIcon(null, g, 0, 0)
        // Then
        assertEquals(60, multipleImageIcon.iconHeight)
        assertEquals(120, multipleImageIcon.iconWidth)
        Mockito.verify(g).drawImage(redImage.image, 0, 0, redImage.imageObserver)
        Mockito.verify(g).drawImage(blueImage.image, 65, 0, blueImage.imageObserver)
        Mockito.verifyNoMoreInteractions(g)
    }

    @Test
    fun test2() {
        // Given
        val blueImage = ImageIcon({}::class.java.classLoader.getResource("blue.png"))
        val redImage = ImageIcon({}::class.java.classLoader.getResource("red.png"))
        val g = Mockito.mock(Graphics::class.java)
        // When
        val multipleImageIcon = MultipleImageIcon(listOf(blueImage, redImage))
        multipleImageIcon.paintIcon(null, g, 0, 0)
        // Then
        assertEquals(60, multipleImageIcon.iconHeight)
        assertEquals(120, multipleImageIcon.iconWidth)
        Mockito.verify(g).drawImage(blueImage.image, 0, 0, redImage.imageObserver)
        Mockito.verify(g).drawImage(redImage.image, 55, 0, blueImage.imageObserver)
        Mockito.verifyNoMoreInteractions(g)
    }

    @Test
    fun testOneImage() {
        // Given
        val blueImage = ImageIcon({}::class.java.classLoader.getResource("blue.png"))
        val g = Mockito.mock(Graphics::class.java)
        // When
        val multipleImageIcon = MultipleImageIcon(listOf(blueImage))
        multipleImageIcon.paintIcon(null, g, 0, 0)
        // Then
        assertEquals(50, multipleImageIcon.iconHeight)
        assertEquals(55, multipleImageIcon.iconWidth)
        Mockito.verify(g).drawImage(blueImage.image, 0, 0, blueImage.imageObserver)
        Mockito.verifyNoMoreInteractions(g)
    }

    @Test
    fun testNoIcons() {
        // Given
        val g = Mockito.mock(Graphics::class.java)
        // When
        val multipleImageIcon = MultipleImageIcon(listOf())
        multipleImageIcon.paintIcon(null, g, 0, 0)
        // Then
        assertEquals(0, multipleImageIcon.iconHeight)
        assertEquals(0, multipleImageIcon.iconWidth)
        Mockito.verifyNoInteractions(g)
    }
}