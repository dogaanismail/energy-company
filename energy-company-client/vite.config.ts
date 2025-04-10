import { defineConfig } from 'vite'
import { configDefaults } from 'vitest/config'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  test: {
    globals: true,
    environment: 'jsdom',
    setupFiles: './src/setup.ts',
    exclude: [...configDefaults.exclude, 'dist', '.idea', '.git', '.cache']
  },
  server: {
    host: '0.0.0.0', 
    port: 5173, 
    strictPort: true,
  },
});