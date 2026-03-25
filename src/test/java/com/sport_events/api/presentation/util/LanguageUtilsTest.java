package com.sport_events.api.presentation.util;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class LanguageUtilsTest {

    @Test
    void normalizeLanguage_extractsPrimaryTagFromSimpleCode() {
        assertThat(LanguageUtils.normalizeLanguage("en")).isEqualTo("en");
    }

    @Test
    void normalizeLanguage_stripsRegionSubtag() {
        assertThat(LanguageUtils.normalizeLanguage("en-US")).isEqualTo("en");
    }

    @Test
    void normalizeLanguage_stripsQualityAndRegion() {
        assertThat(LanguageUtils.normalizeLanguage("uk-UA,uk;q=0.9,en;q=0.8")).isEqualTo("uk");
    }

    @Test
    void normalizeLanguage_stripsQualityWithoutRegion() {
        assertThat(LanguageUtils.normalizeLanguage("pl;q=0.7")).isEqualTo("pl");
    }

    @Test
    void normalizeLanguage_lowercasesResult() {
        assertThat(LanguageUtils.normalizeLanguage("EN")).isEqualTo("en");
    }

    @Test
    void normalizeLanguage_trimsWhitespace() {
        assertThat(LanguageUtils.normalizeLanguage("  fr  ")).isEqualTo("fr");
    }
}
