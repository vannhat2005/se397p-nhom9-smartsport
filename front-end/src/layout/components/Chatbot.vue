<template>
    <div class="chatbot-wrapper">
        <!-- Khung chat khi mở -->
        <div v-if="isOpen" class="chat-window">
            <!-- Header -->
            <div class="chat-header">
                <div class="bot-info">
                    <div class="bot-avatar">
                        <span class="material-symbols-outlined">smart_toy</span>
                    </div>

                    <div>
                        <p class="bot-title">Trợ lý ảo SmartSport AI</p>

                        <div class="bot-status">
                            <span></span>
                            <small>Đang hoạt động</small>
                        </div>
                    </div>
                </div>

                <div class="chat-actions">
                    <button type="button" @click="isOpen = false">
                        <span class="material-symbols-outlined">minimize</span>
                    </button>

                    <button type="button" @click="isOpen = false">
                        <span class="material-symbols-outlined">close</span>
                    </button>
                </div>
            </div>

            <!-- Nội dung tin nhắn -->
            <div class="chat-body">
                <!-- Tin nhắn bot -->
                <div class="message-row bot-message">
                    <div class="message-avatar bot">
                        <span class="material-symbols-outlined">smart_toy</span>
                    </div>

                    <div class="message-content bot-content">
                        <p>
                            Chào bạn! Tôi là trợ lý SmartSport. Tôi có thể giúp bạn tìm sân,
                            hướng dẫn đặt sân hoặc kiểm tra lịch đặt.
                        </p>
                        <small>10:42 AM</small>
                    </div>
                </div>

                <!-- Tin nhắn user mẫu -->
                <div class="message-row user-message">
                    <div class="message-avatar user">
                        <span class="material-symbols-outlined">person</span>
                    </div>

                    <div class="message-content user-content">
                        <p>Tôi muốn tìm sân bóng đá còn trống hôm nay.</p>
                        <small>10:43 AM</small>
                    </div>
                </div>

                <!-- Tin nhắn bot mẫu -->
                <div class="message-row bot-message">
                    <div class="message-avatar bot">
                        <span class="material-symbols-outlined">smart_toy</span>
                    </div>

                    <div class="message-content bot-content">
                        <p>
                            Chức năng chatbot AI sẽ được tích hợp sau khi hoàn thành các chức
                            năng chính của hệ thống.
                        </p>
                        <small>10:43 AM</small>
                    </div>
                </div>
            </div>

            <!-- Gợi ý + input -->
            <div class="chat-footer">
                <div class="suggestions">
                    <button type="button" @click="setMessage('Tìm sân gần tôi')">
                        Tìm sân gần tôi
                    </button>

                    <button type="button" @click="setMessage('Sân giá rẻ')">
                        Sân giá rẻ
                    </button>

                    <button type="button" @click="setMessage('Hướng dẫn đặt sân')">
                        Hướng dẫn đặt sân
                    </button>
                </div>

                <div class="input-row">
                    <input v-model="message" type="text" placeholder="Nhập tin nhắn..." @keyup.enter="sendMessage" />

                    <button type="button" @click="sendMessage">
                        <span class="material-symbols-outlined">send</span>
                    </button>
                </div>

                <p class="bot-note">
                    Được vận hành bởi SmartSport Pro AI
                </p>
            </div>
        </div>

        <!-- Nút bật/tắt chatbot -->
        <button type="button" class="chat-fab" :class="{ active: isOpen }" @click="toggleChat">
            <span class="material-symbols-outlined">
                {{ isOpen ? "chat_bubble" : "smart_toy" }}
            </span>
        </button>
    </div>
</template>

<script>
export default {
    name: "Chatbot",

    data() {
        return {
            isOpen: false,
            message: "",
        };
    },

    methods: {
        toggleChat() {
            this.isOpen = !this.isOpen;
        },

        setMessage(text) {
            this.message = text;
        },

        sendMessage() {
            if (!this.message.trim()) {
                return;
            }

            console.log("Tin nhắn chatbot:", this.message);

            alert(
                "Chatbot sẽ được tích hợp sau khi hoàn thành các chức năng chính của hệ thống."
            );

            this.message = "";
        },
    },
};
</script>

<style scoped>
.chatbot-wrapper {
    position: fixed;
    right: 24px;
    bottom: 24px;
    z-index: 100;
    display: flex;
    flex-direction: column;
    align-items: flex-end;
    gap: 16px;
    font-family: "Lexend", sans-serif;
}

/* Chat Window */
.chat-window {
    width: 400px;
    height: 600px;
    background: #ffffff;
    border: 1px solid rgba(195, 197, 217, 0.35);
    border-radius: 20px;
    box-shadow: 0 25px 60px rgba(15, 23, 42, 0.28);
    overflow: hidden;
    display: flex;
    flex-direction: column;
}

/* Header */
.chat-header {
    background: #003ec7;
    color: #ffffff;
    padding: 16px;
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.bot-info {
    display: flex;
    align-items: center;
    gap: 12px;
}

.bot-avatar {
    width: 40px;
    height: 40px;
    border-radius: 999px;
    background: rgba(255, 255, 255, 0.18);
    border: 1px solid rgba(255, 255, 255, 0.28);
    display: flex;
    align-items: center;
    justify-content: center;
}

.bot-avatar .material-symbols-outlined {
    font-variation-settings: "FILL" 1;
}

.bot-title {
    margin: 0;
    font-size: 14px;
    font-weight: 700;
}

.bot-status {
    margin-top: 2px;
    display: flex;
    align-items: center;
    gap: 6px;
}

.bot-status span {
    width: 6px;
    height: 6px;
    background: #4ade80;
    border-radius: 999px;
}

.bot-status small {
    font-size: 10px;
    opacity: 0.85;
}

.chat-actions {
    display: flex;
    align-items: center;
    gap: 6px;
}

.chat-actions button {
    border: none;
    background: transparent;
    color: #ffffff;
    border-radius: 6px;
    padding: 4px;
    cursor: pointer;
    display: flex;
    transition: 0.2s;
}

.chat-actions button:hover {
    background: rgba(255, 255, 255, 0.12);
}

/* Body */
.chat-body {
    flex: 1;
    padding: 16px;
    background: #ffffff;
    overflow-y: auto;
    display: flex;
    flex-direction: column;
    gap: 16px;
}

.message-row {
    display: flex;
    gap: 8px;
    max-width: 85%;
}

.bot-message {
    align-self: flex-start;
}

.user-message {
    align-self: flex-end;
    flex-direction: row-reverse;
}

.message-avatar {
    width: 32px;
    height: 32px;
    border-radius: 999px;
    flex-shrink: 0;
    display: flex;
    align-items: center;
    justify-content: center;
}

.message-avatar.bot {
    background: #0052ff;
    color: #ffffff;
}

.message-avatar.user {
    background: #ededfb;
    color: #003ec7;
    border: 1px solid #c3c5d9;
}

.message-avatar .material-symbols-outlined {
    font-size: 18px;
}

.message-content {
    padding: 12px;
    border-radius: 18px;
    box-shadow: 0 1px 4px rgba(15, 23, 42, 0.08);
}

.message-content p {
    margin: 0;
    font-size: 14px;
    line-height: 1.5;
}

.message-content small {
    display: block;
    margin-top: 4px;
    font-size: 10px;
}

.bot-content {
    background: #ffffff;
    border: 1px solid rgba(195, 197, 217, 0.35);
    color: #191b25;
    border-top-left-radius: 4px;
}

.bot-content small {
    color: #737688;
}

.user-content {
    background: #003ec7;
    color: #ffffff;
    border-top-right-radius: 4px;
}

.user-content small {
    opacity: 0.75;
    text-align: right;
}

/* Footer */
.chat-footer {
    padding: 16px;
    background: #ffffff;
    border-top: 1px solid rgba(195, 197, 217, 0.35);
}

.suggestions {
    display: flex;
    flex-wrap: wrap;
    gap: 8px;
    margin-bottom: 14px;
}

.suggestions button {
    border: 1px solid rgba(195, 197, 217, 0.45);
    background: #f3f2ff;
    color: #003ec7;
    border-radius: 999px;
    padding: 6px 12px;
    font-size: 12px;
    cursor: pointer;
    transition: 0.2s;
}

.suggestions button:hover {
    background: #dde1ff;
}

.input-row {
    display: flex;
    align-items: center;
    gap: 8px;
}

.input-row input {
    flex: 1;
    border: none;
    outline: none;
    background: #ededfb;
    border-radius: 12px;
    padding: 12px 14px;
    font-size: 14px;
    color: #191b25;
    font-family: inherit;
}

.input-row input:focus {
    box-shadow: 0 0 0 2px rgba(0, 62, 199, 0.18);
}

.input-row button {
    border: none;
    background: #003ec7;
    color: #ffffff;
    border-radius: 10px;
    padding: 10px;
    cursor: pointer;
    display: flex;
    transition: 0.2s;
}

.input-row button:hover {
    background: #0052ff;
}

.input-row button:active {
    transform: scale(0.92);
}

.bot-note {
    margin: 10px 0 0;
    text-align: center;
    color: #737688;
    font-size: 10px;
}

/* Floating Button */
.chat-fab {
    width: 64px;
    height: 64px;
    border: none;
    border-radius: 999px;
    background: #003ec7;
    color: #ffffff;
    box-shadow: 0 18px 40px rgba(0, 62, 199, 0.32);
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    transition: 0.2s;
}

.chat-fab:hover {
    transform: scale(1.08);
}

.chat-fab:active {
    transform: scale(0.95);
}

.chat-fab.active {
    box-shadow: 0 0 0 8px rgba(0, 62, 199, 0.12),
        0 18px 40px rgba(0, 62, 199, 0.32);
}

.chat-fab .material-symbols-outlined {
    font-size: 32px;
    font-variation-settings: "FILL" 1;
}

/* Responsive */
@media (max-width: 520px) {
    .chatbot-wrapper {
        right: 16px;
        bottom: 16px;
    }

    .chat-window {
        width: calc(100vw - 32px);
        height: 560px;
    }

    .chat-fab {
        width: 56px;
        height: 56px;
    }
}
</style>