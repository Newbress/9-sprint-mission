// API 엔드포인트 설정
const API_BASE_URL = 'http://localhost:1112/api';
const ENDPOINTS = {
    USERS: `${API_BASE_URL}/users/findAll`,
    BINARY_CONTENT: `${API_BASE_URL}/binaryContent/find`
};

document.addEventListener('DOMContentLoaded', () => {
    fetchAndRenderUsers();
});

async function fetchAndRenderUsers() {
    try {
        const response = await fetch(ENDPOINTS.USERS);
        if (!response.ok) throw new Error('사용자 목록을 불러오는데 실패했습니다.');
        const users = await response.json();
        await renderUserList(users);
    } catch (error) {
        console.error('Error fetching users:', error);
    }
}

async function fetchUserProfile(profileId) {
    try {
        const response = await fetch(`${ENDPOINTS.BINARY_CONTENT}?id=${profileId}`);
        if (!response.ok) throw new Error(`서버 응답 오류: ${response.status}`);
        const data = await response.json();
        const profile = Array.isArray(data) ? data[0] : data;
        if (!profile || !profile.bytes) {
            throw new Error('이미지 데이터 없음');
        }
        return `data:${profile.contentType};base64,${profile.bytes}`;
    } catch (error) {
        console.warn(`이미지 로드 실패 (${profileId})`);
    }
}

async function renderUserList(users) {
    const userListElement = document.getElementById('userList');
    if (!userListElement) return;

    userListElement.innerHTML = '';

    for (const user of users) {
        const userElement = document.createElement('div');
        userElement.className = 'user-item';

        const profileUrl = await fetchUserProfile(user.profileId);

        userElement.innerHTML = `
            <img src="${profileUrl}" alt="${user.username}" class="user-avatar">
            <div class="user-info">
                <div class="user-name">${user.username}</div>
                <div class="user-email">${user.email}</div>
            </div>
            <div class="status-badge ${user.online ? 'online' : 'offline'}">
                ${user.online ? '온라인' : '오프라인'}
            </div>
        `;

        userListElement.appendChild(userElement);
    }
}