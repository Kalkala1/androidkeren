# Implementation Plan - Ocean Blue & Happy Style Redesign

This plan outlines the redesign of the "Presensi & Jurnal Kelas" application to a "Happy Style" using an "Ocean Blue Gradient" palette.

## User Review Required

> [!IMPORTANT]
> **Design Shift**:
> We are moving from the dark "Starry Night" aesthetic to a bright, cheerful "Happy Style". This includes softer edges, playful accents, and a light-themed focus by default.
>
> **Ocean Gradient**:
> Backgrounds will use a vertical gradient from a deep ocean blue to a bright turquoise/mist blue.

## Proposed Changes

### Theme & Styling

#### [MODIFY] [Color.kt](file:///C:/Users/KAYLA/AndroidStudioProjects/tugas/app/src/main/java/com/example/tugas/ui/theme/Color.kt)
- Define colors: `OceanDeep` (dark blue), `OceanWave` (medium blue), `OceanMist` (light turquoise), `HappySun` (bright yellow for accents), `SoftCoral` (friendly pink for errors/warnings).

#### [MODIFY] [Theme.kt](file:///C:/Users/KAYLA/AndroidStudioProjects/tugas/app/src/main/java/com/example/tugas/ui/theme/Theme.kt)
- Update `LightColorScheme` with the ocean and happy palette.
- Update `DarkColorScheme` to a deeper "Deep Sea" variant.
- Re-enable `dynamicColor` option but default to the new branded colors.

---

### UI Layer Redesign (Happy Style)

#### [MODIFY] [Screens.kt](file:///C:/Users/KAYLA/AndroidStudioProjects/tugas/app/src/main/java/com/example/tugas/ui/Screens.kt)
- **General Layout**: Use `Brush.verticalGradient` with ocean colors for all screen backgrounds.
- **Card Design**: Increase `RoundedCornerShape` to 28dp or 32dp for a softer, "happier" feel. Add subtle shadows.
- **Home Screen**:
    - Friendly greeting with emojis (e.g., "Halo Guru! 🌊").
    - Cards with bright icons and cheerful typography.
- **Student & Journal Screens**:
    - Use "bubbly" card styles.
    - Cheerful floating action buttons.
- **Attendance Screen**:
    - Redesign attendance chips to look like rounded "bubbles".
    - Use bright colors for "Hadir" (Green/Turquoise) and "Izin" (Yellow).

#### [MODIFY] [MainActivity.kt](file:///C:/Users/KAYLA/AndroidStudioProjects/tugas/app/src/main/java/com/example/tugas/MainActivity.kt)
- Update `NavigationBar` with a soft, translucent ocean look.
- Style `NavigationBarItem` with "happy" selected indicators.

---

## Verification Plan

### Manual Verification
- Deploy to emulator/device.
- Verify the "Happy Style" feel: are the corners soft? is the color palette cheerful?
- Check readability on the ocean gradient backgrounds.
- Ensure all screens transition smoothly.
