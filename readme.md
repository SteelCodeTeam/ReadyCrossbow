# ReadyCrossbow 🏹

**ReadyCrossbow** is a lightweight Minecraft mod that ensures enemies are always combat-ready. No more grace periods: if a mob spawns with a crossbow, they spawn with it locked and loaded.

## 🚀 Features

In vanilla Minecraft, mobs like Pillagers or Piglins need to charge their crossbows before their first shot. This mod removes that delay:

*   **Auto-Charge on Spawn:** Any mob spawning with a crossbow will automatically have a projectile loaded.
*   **Instant Aggression:** Combat encounters are more dynamic and challenging, as enemies can fire the moment they detect you.
*   **Smart State Management:** The mod forcefully updates the mob's AI state to "Ready to Attack" if they are holding a charged crossbow, preventing any "waiting" animations.

## 🛠️ Technical Details

- **Compatibility:** Works with any entity implementing the `CrossbowAttackMob` interface (Pillagers, Piglins, etc.).
- **Performance:** Extremely lightweight; logic only triggers during the spawn event and AI tick updates.
- **Platform:** Built for **NeoForge**.
- **Requirement:** Java 21 and Minecraft 1.21+.

## 💻 Installation

Use CurseForge or Modrinth to install the mod just search for "ReadyCrossbow" and download the latest version.

### Manual Installation
1. Ensure you have the **NeoForge** loader installed.
2. Download the mod's `.jar` file.
3. Place it into your Minecraft `mods` folder.

