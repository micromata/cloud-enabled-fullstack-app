/** @type {import('tailwindcss').Config} */
export default {
  content: ['./src/**/*.{html,js,svelte,ts}'],
  theme: {
    extend: {},
  },
  plugins: [],
  safelist: [
    {
      pattern: /bg-./
    },
    {
      pattern: /text-./
    },
    {
      pattern: /border-./
    }
  ]
}

