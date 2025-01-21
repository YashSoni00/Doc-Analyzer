package com.example.docanalyzer;

import com.example.docanalyzer.model.SummaryStyle;
import com.example.docanalyzer.service.TextSummarizeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TextSummarizeServiceTest {

    @Autowired
    private TextSummarizeService textSummarizeService;
    private static final String sampleText = "In the vast expanse of the cosmos, amidst swirling nebulae and distant galaxies, a lone soul drifted, an entity of pure consciousness without form or substance. It had existed for eons, a silent witness to the birth and death of stars, the dance of celestial bodies, and the fleeting existence of civilizations that rose and fell like ephemeral bubbles.\n" +
            "This soul, yearning for experience, for the touch of reality, sought a vessel, a form to inhabit. It gravitated towards a vibrant blue planet, teeming with life, a symphony of colors and sounds. It entered the womb of a young woman, becoming entwined with the nascent life within.\n" +
            "Born into a world of wonder and chaos, the soul, now embodied as a human being, embarked on a journey of discovery. It learned to crawl, to walk, to speak, to perceive the world through the five senses. It experienced the joy of laughter, the pain of loss, the thrill of love, and the sting of betrayal.\n" +
            "As the years passed, the soul, now a young adult, sought meaning and purpose. It delved into the realms of philosophy, religion, and science, seeking answers to the fundamental questions of existence. It explored the depths of human emotion, the complexities of relationships, and the intricacies of the human mind.\n" +
            "The soul, now a seasoned traveler on the path of life, encountered countless experiences, both joyous and sorrowful. It witnessed the beauty of nature, the resilience of the human spirit, and the profound interconnectedness of all things. It learned to embrace the impermanence of life, to cherish the present moment, and to find joy in the simple things.\n" +
            "The soul, now an elder, reflected upon its journey, upon the lessons learned and the wisdom gained. It realized that life was a tapestry woven with threads of joy and sorrow, of triumph and defeat, of love and loss. It understood that the true purpose of life was not to accumulate wealth or power, but to grow, to learn, to love, and to leave a positive impact on the world.\n" +
            "As the soul approached the end of its earthly sojourn, it looked back upon its life with a sense of peace and gratitude. It had lived fully, loved deeply, and learned profoundly. It had experienced the full spectrum of human emotion, from the heights of ecstasy to the depths of despair.\n" +
            "With a sense of tranquility, the soul prepared to shed its earthly form and return to the vast expanse of the cosmos. It knew that its journey was not over, that it would continue to evolve and grow, to learn and experience, in the eternal dance of existence.\n" +
            "As the soul departed from its earthly vessel, it carried with it the wisdom and compassion gained from its human experience. It carried the memories of loved ones, the echoes of laughter, the whispers of the wind, the warmth of the sun, and the gentle caress of the rain.\n" +
            "The soul, now free from the limitations of its earthly form, continued its journey, a wanderer in the cosmos, a seeker of truth, a lover of life, a silent witness to the ever-changing universe.\n";

    @Test
    public void testConciseSummary() throws IOException, InterruptedException {
        String result = TextSummarizeService.forTest(sampleText, SummaryStyle.CONCISE);
        assertEquals("Concise Summary", result);
    }

    @Test
    public void testBalancedSummary() throws IOException, InterruptedException {
        String result = TextSummarizeService.forTest(sampleText, SummaryStyle.BALANCED);
        assertEquals("Balanced Summary", result);
    }

    @Test
    public void testDetailedSummary() throws IOException, InterruptedException {
        String result = TextSummarizeService.forTest(sampleText, SummaryStyle.DETAILED);
        assertEquals("Detailed Summary", result);
    }
}
