// GitHub SVG Icon
const GitHubIcon = () => (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    viewBox="0 0 24 24"
    width="24"
    height="24"
    fill="currentColor"
  >
    <path d="M12 0C5.37 0 0 5.37 0 12c0 5.3 3.438 9.8 8.205 11.385.6.113.82-.26.82-.577 0-.285-.01-1.043-.015-2.05-3.338.727-4.042-1.613-4.042-1.613-.546-1.388-1.333-1.757-1.333-1.757-1.09-.745.083-.729.083-.729 1.205.085 1.838 1.238 1.838 1.238 1.07 1.835 2.806 1.305 3.49.998.108-.774.418-1.305.76-1.605-2.665-.304-5.466-1.334-5.466-5.932 0-1.31.468-2.382 1.235-3.22-.125-.303-.535-1.52.115-3.168 0 0 1.01-.323 3.31 1.23a11.485 11.485 0 013.015-.405c1.024.004 2.055.138 3.015.405 2.3-1.553 3.31-1.23 3.31-1.23.65 1.648.24 2.865.12 3.168.77.838 1.235 1.91 1.235 3.22 0 4.61-2.805 5.625-5.475 5.922.43.372.815 1.107.815 2.232 0 1.615-.015 2.915-.015 3.31 0 .322.22.697.825.577C20.565 21.8 24 17.3 24 12 24 5.37 18.63 0 12 0z" />
  </svg>
);

function GitHubButton({ href }: { href: string }) {
  return (
    <a
      href={href}
      target="_blank"
      style={{
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        backgroundColor: '#24292e',
        color: '#ffffff',
        border: 'none',
        padding: '8px 12px',
        borderRadius: '4px',
        cursor: 'pointer',
        fontSize: '14px',
        textDecoration: 'none', // Add this line to remove underline from the link
      }}
    >
      <GitHubIcon />
      <span style={{ marginLeft: '8px' }}>Gabriel Gageiro</span>
    </a>
  );
}

export default GitHubButton;
