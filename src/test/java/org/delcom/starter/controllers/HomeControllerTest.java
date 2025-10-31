package org.delcom.starter.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest {

    private final HomeController controller = new HomeController();

    // ============================
    // 1️⃣ Tes Hello Endpoint
    // ============================
    @Test
    @DisplayName("Mengembalikan pesan sambutan default dengan benar")
    void hello_ShouldReturnWelcomeMessage() {
        String result = controller.hello();
        assertEquals("Hay Abdullah, selamat datang di pengembangan aplikasi dengan Spring Boot!", result);
    }

    @Test
    @DisplayName("Mengembalikan sapaan personal yang benar")
    void sayHello_ShouldReturnPersonalGreeting() {
        String result = controller.sayHello("Niken");
        assertEquals("Hello, Niken!", result);
    }

    // ============================
    // 2️⃣ Tes Informasi NIM
    // ============================
    @Test
    @DisplayName("Mengembalikan informasi NIM dengan benar untuk Sarjana Informatika")
    void informasiNim_ShouldReturnFormattedInfo_ForInformatika() {
        String nim = "11S24052";
        String expected = "Informasi NIM 11S24052:<br>> Program Studi: Sarjana Informatika<br>> Angkatan: 2024<br>> Urutan: 52";
        String result = controller.informasiNim(nim);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Mengembalikan informasi NIM dengan benar untuk program studi lain")
    void informasiNim_ShouldReturnFormattedInfo_ForOtherPrograms() {
        String nim = "21S23001";
        String expected = "Informasi NIM 21S23001:<br>> Program Studi: Sarjana Manajemen Rekayasa<br>> Angkatan: 2023<br>> Urutan: 1";
        String result = controller.informasiNim(nim);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Mengembalikan 'Program Studi Tidak Dikenal' untuk kode prefix tidak valid")
    void informasiNim_ShouldReturnUnknownProgram_ForInvalidPrefix() {
        String nim = "99X25001";
        String result = controller.informasiNim(nim);
        assertTrue(result.contains("Program Studi Tidak Dikenal"));
    }

    // ============================
    // 3️⃣ Tes getProgramStudi (private)
    // ============================
    @Test
    @DisplayName("getProgramStudi mengembalikan nama program studi yang benar")
    void getProgramStudi_ShouldReturnCorrectPrograms() throws Exception {
        var method = HomeController.class.getDeclaredMethod("getProgramStudi", String.class);
        method.setAccessible(true);

        assertEquals("Sarjana Informatika", method.invoke(controller, "11S"));
        assertEquals("Sarjana Sistem Informasi", method.invoke(controller, "12S"));
        assertEquals("Sarjana Teknik Elektro", method.invoke(controller, "14S"));
        assertEquals("Sarjana Manajemen Rekayasa", method.invoke(controller, "21S"));
        assertEquals("Sarjana Teknik Metalurgi", method.invoke(controller, "22S"));
        assertEquals("Sarjana Teknik Bioproses", method.invoke(controller, "31S"));
        assertEquals("Diploma Teknik Rekayasa Perangkat Lunak", method.invoke(controller, "114"));
        assertEquals("Diploma Teknologi Informasi", method.invoke(controller, "113"));
        assertEquals("Diploma Teknologi Komputer", method.invoke(controller, "133"));
    }

    @Test
    @DisplayName("getProgramStudi mengembalikan 'Program Studi Tidak Dikenal' untuk kode tidak valid")
    void getProgramStudi_ShouldReturnUnknownForInvalidCode() throws Exception {
        var method = HomeController.class.getDeclaredMethod("getProgramStudi", String.class);
        method.setAccessible(true);

        assertEquals("Program Studi Tidak Dikenal", method.invoke(controller, "99X"));
        assertEquals("Program Studi Tidak Dikenal", method.invoke(controller, ""));
    }

    // ============================
    // 4️⃣ Tes Perolehan Nilai
    // ============================
    @Test
    @DisplayName("Perolehan Nilai menampilkan hasil decoding Base64 dengan benar")
    void perolehanNilai_ShouldReturnDecodedValue() {
        String text = "Nilai Akhir: 95";
        String encoded = Base64.getEncoder().encodeToString(text.getBytes());

        String result = controller.perolehanNilai(encoded);

        assertEquals("Perolehan Nilai: Nilai Akhir: 95", result);
    }

    // ============================
    // 5️⃣ Tes Perbedaan L dan Kebalikannya
    // ============================
    @Test
    @DisplayName("PerbedaanL menampilkan perbedaan karakter dengan benar")
    void perbedaanL_ShouldReturnDifferences() {
        String text = "ABCD";
        String encoded = Base64.getEncoder().encodeToString(text.getBytes());

        String result = controller.perbedaanL(encoded);

        assertTrue(result.contains("Teks Asli: ABCD"));
        assertTrue(result.contains("Kebalikannya: DCBA"));
        assertTrue(result.contains("Perbedaannya"));
    }

    @Test
    @DisplayName("PerbedaanL menangani palindrome dengan benar")
    void perbedaanL_ShouldHandlePalindrome() {
        String text = "KAYAK";
        String encoded = Base64.getEncoder().encodeToString(text.getBytes());

        String result = controller.perbedaanL(encoded);

        assertTrue(result.contains("Teks Asli: KAYAK"));
        assertTrue(result.contains("Kebalikannya: KAYAK"));
        assertTrue(result.contains("Perbedaannya: "));
    }

    // ============================
    // 6️⃣ Tes Paling Ter
    // ============================
    @Test
    @DisplayName("PalingTer menampilkan kata terpanjang dan terpendek dengan benar")
    void palingTer_ShouldReturnShortestAndLongestWords() {
        String text = "Belajar Spring boot";
        String encoded = Base64.getEncoder().encodeToString(text.getBytes());

        String result = controller.palingTer(encoded);

        assertTrue(result.contains("Kalimat: Belajar Spring boot"));
        assertTrue(result.contains("Paling Pendek: boot"));
        assertTrue(result.contains("Paling Panjang: Belajar"));
    }

}