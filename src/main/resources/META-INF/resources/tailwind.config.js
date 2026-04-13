/** @type {import('tailwindcss').Config} */
export default {
  content: ['./src/**/*.{html,js,ts,jsx,tsx,java}'],
  theme: {
    extend: {
      // ─── Typography ───────────────────────────────────────────────
      fontSize: {
        xs: ['var(--aura-font-size-xs)', { lineHeight: 'var(--aura-line-height-xs)' }],
        s:  ['var(--aura-font-size-s)',  { lineHeight: 'var(--aura-line-height-s)' }],
        m:  ['var(--aura-font-size-m)',  { lineHeight: 'var(--aura-line-height-m)' }],
        l:  ['var(--aura-font-size-l)',  { lineHeight: 'var(--aura-line-height-l)' }],
        xl: ['var(--aura-font-size-xl)', { lineHeight: 'var(--aura-line-height-xl)' }],
      },

      lineHeight: {
        xs: 'var(--aura-line-height-xs)',
        s: 'var(--aura-line-height-s)',
        m: 'var(--aura-line-height-m)',
        l: 'var(--aura-line-height-l)',
        xl: 'var(--aura-line-height-xl)',
      },

      // ─── Spacing — gap ────────────────────────────────────────────
      gap: {
        xs: 'var(--vaadin-gap-xs)',
        s: 'var(--vaadin-gap-s)',
        m: 'var(--vaadin-gap-m)',
        l: 'var(--vaadin-gap-l)',
        xl: 'var(--vaadin-gap-xl)',
      },

      // ─── Spacing — padding ────────────────────────────────────────
      padding: {
        xs: 'var(--vaadin-padding-xs)',
        s: 'var(--vaadin-padding-s)',
        m: 'var(--vaadin-padding-m)',
        l: 'var(--vaadin-padding-l)',
        xl: 'var(--vaadin-padding-xl)',
        'block-container':  'var(--vaadin-padding-block-container)',
        'inline-container': 'var(--vaadin-padding-inline-container)',
      },

      // ─── Colors ───────────────────────────────────────────────────
      colors: {
        // Semantic surface defaults
        container: 'var(--vaadin-background-container)',

        // Background / badge colors
        red: 'var(--aura-red)',
        orange: 'var(--aura-orange)',
        yellow: 'var(--aura-yellow)',
        green: 'var(--aura-green)',
        blue: 'var(--aura-blue)',
        purple: 'var(--aura-purple)',

        // Text-safe variants
        'red-text': 'var(--aura-red-text)',
        'orange-text': 'var(--aura-orange-text)',
        'yellow-text': 'var(--aura-yellow-text)',
        'green-text': 'var(--aura-green-text)',
        'blue-text': 'var(--aura-blue-text)',
        'purple-text': 'var(--aura-purple-text)',

        // Text hierarchy
        text: {
          secondary: 'var(--vaadin-text-color-secondary)',
          disabled: 'var(--vaadin-text-color-disabled)',
        },

        // Borders
        border: {
          secondary: 'var(--vaadin-border-color-secondary)',
        },

        // Accent
        accent: 'var(--aura-accent-color)',
        'accent-contrast': 'var(--aura-accent-contrast-color)',
        'accent-text': 'var(--aura-accent-text-color)',
      },

      // ─── Shadows ──────────────────────────────────────────────────
      boxShadow: {
        xs: 'var(--aura-shadow-xs)',
        s: 'var(--aura-shadow-s)',
        m: 'var(--aura-shadow-m)',
      },

      // ─── Border radius ────────────────────────────────────────────
      borderRadius: {
        sm: 'var(--vaadin-radius-s)',
        md: 'var(--vaadin-radius-m)',
        lg: 'var(--vaadin-radius-l)',
      },
    },
  },
  plugins: [],
}
